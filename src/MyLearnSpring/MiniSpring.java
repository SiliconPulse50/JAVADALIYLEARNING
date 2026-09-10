package MyLearnSpring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.util.*;

public class MiniSpring {

    public static void main(String[] args) throws Exception {

        // 1. 要扫描的类列表（注意：是 UserServiceImpl，不是 UserService 接口）
        List<Class<?>> allClasses = Arrays.asList(
                UserServiceImpl.class,
                OrderService.class,
                NoAnnotationClass.class
        );

        Map<String, Object> container = new HashMap<>();

        // ========================================================
        // 【第一轮】第一板斧：创建所有原始对象
        // ========================================================
        System.out.println("===== 阶段一：创建 Bean 实例 =====");
        for (Class<?> clazz : allClasses) {
            MyComponent anno = clazz.getAnnotation(MyComponent.class);
            if (anno == null) {
                System.out.println("【跳过】" + clazz.getSimpleName() + " 没有 @MyComponent");
                continue;
            }
            String beanName = anno.value().isEmpty()
                    ? clazz.getSimpleName().toLowerCase()
                    : anno.value();

            Object instance = clazz.getDeclaredConstructor().newInstance();
            container.put(beanName, instance);
            System.out.println("创建 Bean: " + beanName + " (原始类: " + clazz.getSimpleName() + ")");
        }

        // ========================================================
        // 【第二轮】第三板斧：注入依赖（field.set）
        // ========================================================
        System.out.println("\n===== 阶段二：依赖注入 =====");
        for (Class<?> clazz : allClasses) {
            MyComponent anno = clazz.getAnnotation(MyComponent.class);
            if (anno == null) continue;

            String beanName = anno.value().isEmpty()
                    ? clazz.getSimpleName().toLowerCase()
                    : anno.value();
            Object instance = container.get(beanName);

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getAnnotation(MyAutowired.class) != null) {
                    Class<?> fieldType = field.getType();
                    String depName = fieldType.getSimpleName().toLowerCase();
                    Object dependency = container.get(depName);

                    if (dependency == null) {
                        throw new RuntimeException("找不到依赖: " + fieldType.getName());
                    }
                    field.setAccessible(true);
                    field.set(instance, dependency);
                    System.out.println("【注入】" + clazz.getSimpleName()
                            + "." + field.getName() + " ← " + fieldType.getSimpleName());
                }
            }
        }
// ========================================================
// 【第三轮】第二板斧：调用初始化方法（在原始对象上，还没代理）
// ========================================================
        System.out.println("\n===== 阶段三：调用初始化方法 =====");
        for (Class<?> clazz : allClasses) {
            MyComponent anno = clazz.getAnnotation(MyComponent.class);
            if (anno == null) continue;

            String beanName = anno.value().isEmpty()
                    ? clazz.getSimpleName().toLowerCase()
                    : anno.value();
            Object instance = container.get(beanName);  // ← 此时还是原始对象

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getAnnotation(MyInit.class) != null) {
                    method.setAccessible(true);
                    method.invoke(instance);  // ✅ 原始对象执行原始方法，合法！
                    System.out.println("【框架】调用了初始化方法: " + method.getName());
                }
            }
        }

// ========================================================
// 【第四轮】AOP：生成代理对象，替换容器里的原始对象
// ========================================================
        System.out.println("\n===== 阶段四：生成 AOP 代理 =====");
        for (Class<?> clazz : allClasses) {
            MyComponent anno = clazz.getAnnotation(MyComponent.class);
            if (anno == null) continue;

            String beanName = anno.value().isEmpty()
                    ? clazz.getSimpleName().toLowerCase()
                    : anno.value();
            Object instance = container.get(beanName);  // 拿到完整对象

            if (hasLogAnnotation(clazz)) {
                Object proxy = createProxy(instance);
                container.put(beanName, proxy);   // ★★★ 关键：把代理放回容器 ★★★
                System.out.println("【AOP】为 " + clazz.getSimpleName()
                        + " 生成了代理: " + proxy.getClass().getName());
            }
        }
        // ========================================================
        // 验证
        // ========================================================
        System.out.println("\n===== 验证成果 =====");
        System.out.println("容器内容: " + container);

        // 关键：用【接口】来强转
        UserService user = (UserService) container.get("userService");
        if (user != null) {
            user.hello();
        }
    }

    // 判断类里有没有方法贴了 @MyLog
    static boolean hasLogAnnotation(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(MyLog.class) != null) {
                return true;
            }
        }
        return false;
    }

    // 创建 JDK 动态代理
    static Object createProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    MyLog logAnno = method.getAnnotation(MyLog.class);
                    if (logAnno != null) {
                        System.out.println("【AOP日志】开始: " + method.getName());
                        Object result = method.invoke(target, args);
                        System.out.println("【AOP日志】结束: " + method.getName());
                        return result;
                    }
                    return method.invoke(target, args);
                }
        );
    }
}

// ============================================================
// 注解定义
// ============================================================

//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
//@interface MyComponent {
//    String value() default "";
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.FIELD)
//@interface MyAutowired {
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@interface MyLog {
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@interface MyInit {
//}

// ============================================================
// 业务类
// ============================================================

// 接口
interface UserService {
    @MyLog
    void hello();
}

// 实现类
@MyComponent("userService")
class UserServiceImpl implements UserService {

    @MyAutowired
    private OrderService orderService;

    @MyInit
    public void init() {
        System.out.println("【初始化】UserServiceImpl 的 init() 被调用了");
    }

    @MyLog
    @Override
    public void hello() {
        System.out.println("UserServiceImpl 执行了！");
        if (orderService != null) {
            System.out.println("【验证】orderService 已成功注入: " + orderService);
            orderService.doSomething();
        } else {
            System.out.println("【失败】orderService 是 null");
        }
    }
}

@MyComponent
class OrderService {
    public void doSomething() {
        System.out.println("OrderService 正在执行核心业务逻辑！");
    }
}

class NoAnnotationClass {
}