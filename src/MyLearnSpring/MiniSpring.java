package MyLearnSpring;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class MiniSpring {

    public static void main(String[] args) throws Exception {
        // 1. 假装扫描了硬盘上的 com.test 包，拿到了所有类的 Class 对象（这里简化，只拿两个）
        List<Class<?>> allClasses = Arrays.asList(UserService.class, OrderService.class, NoAnnotationClass.class);

        // 2. 建立一个容器（就是个 Map），用来存放创建好的对象
        Map<String, Object> container = new HashMap<>();
        //key是名字，values是创建好的对象
        // 3. 遍历所有类，这是反射 + 注解的灵魂！
        for (Class<?> clazz : allClasses) {
            // 【好兄弟 A（注解）上场】
            // 调用反射 API：获取该类头上是否贴了 MyComponent 这个标签
            MyComponent annotation = clazz.getAnnotation(MyComponent.class);

            // 4. 如果注解不为 null（说明有标记），执行【第一板斧：创建对象】
            if (annotation != null) {
                // 获取注解里写的名字，如果没写就用类名小写作为默认名
                String beanName = annotation.value().isEmpty() ? clazz.getSimpleName().toLowerCase() : annotation.value();

                // 【第一板斧】暴力创建对象（就是调用无参构造器）
                Object instance = clazz.getDeclaredConstructor().newInstance();

                // ========== 【第二板斧】动态调用初始化方法 ==========
                // 1. 获取当前类的所有方法（包括私有的）
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    // 2. 检查这个方法上是否贴了 @MyInit 标签
                    MyInit initAnno = method.getAnnotation(MyInit.class);
                    if (initAnno != null) {
                        // 3. 如果方法是 private 的，砸锁
                        method.setAccessible(true);
                        // 4. 执行这个方法（传入 instance，因为它是成员方法）
                        method.invoke(instance);
                        System.out.println("【框架】成功调用初始化方法: " + method.getName());
                    }
                }
// ========== 【第二板斧】结束 ==========


                // 放进容器里存起来
                container.put(beanName, instance);
                System.out.println("【Spring 启动】成功创建 Bean: " + beanName);
            } else {
                System.out.println("【跳过】" + clazz.getSimpleName() + " 没有 @MyComponent 注解，忽略");
            }
        }

        // 5. 验证成果：从容器里拿出来用
        System.out.println("容器里的对象：" + container);
        UserService user = (UserService) container.get("userService");
        if (user != null) user.hello();
    }
}

//// ---- 定义两个用于测试的类 ----
//@MyComponent("userService")
//class UserService {
//    @MyInit
//    public void init() {
//        System.out.println("【第二板斧】UserService 的 init() 方法被框架自动调用了！");
//    }
//    public void hello() { System.out.println("UserService 执行了！"); }
//}
//
//@MyComponent // 没写名字，默认就用类名小写 "orderService"
//class OrderService { }
//
//class NoAnnotationClass { } // 没贴注解，框架绝不管它
@MyComponent("userService")
class UserService {

    // 贴标签！告诉框架：我需要你给我找个 OrderService 塞进来
    @MyAutowired
    private OrderService orderService;  // 注意：这里是 private，且没有赋值

    // 为了验证注入是否成功，我们加一个打印方法
    public void hello() {
        System.out.println("UserService 执行了！");
        if (orderService != null) {
            System.out.println("【验证】orderService 已成功注入，地址是：" + orderService);
            orderService.doSomething(); // 调用一下看看是否真的能用
        } else {
            System.out.println("【失败】orderService 是 null，注入没成功！");
        }
    }
}

@MyComponent
class OrderService {
    public void doSomething() {
        System.out.println("OrderService 正在执行核心业务逻辑！");
    }
}

class NoAnnotationClass { }