# MiniSpring ——  Spring 核心原理


## 📖 项目简介

MiniSpring 是我在学习 Java 反射时做的的超轻量级 IoC 容器。它**不是 Spring 的替代品**，而是一个 **「教学演示框架」**，用于揭开 Spring 启动过程的神秘面纱。

当你写下 `@Autowired` 时，你有没有好奇过：
- **Spring 是怎么找到你要的依赖的？**
- **它凭什么能往 private 字段里塞值？**
- **`@PostConstruct` 标注的方法是谁调用的？**

**MiniSpring 会用不到 150 行代码，给你所有答案。**

---

## ✨ 核心功能

| 功能 | 对应 Spring 特性 | 实现技术 |
| :--- | :--- | :--- |
| **IoC 容器管理** | `@Component` 扫描与实例化 | `Class.forName` + `newInstance()` |
| **字段注入** | `@Autowired` 依赖注入 | `field.setAccessible(true)` + `field.set()` |
| **生命周期回调** | `@PostConstruct` 初始化方法 | `method.invoke()` + `setAccessible(true)` |
| **容器存储** | `ApplicationContext` 的 Map 实现 | `ConcurrentHashMap`（简化为 `HashMap`） |

---

## 🏗️ 项目结构

```
MyLearnSpring/
├── MiniSpring.java          # 框架主程序（核心逻辑 80 行）
├── MyComponent.java         # @Component 注解
├── MyAutowired.java         # @Autowired 注解
├── MyPostConstruct.java     # @PostConstruct 注解
└── README.md                # 你现在看的文档
```

---

## 🔧 快速开始

### 1. 环境要求
- JDK 8 及以上（推荐 JDK 17/21）
- IntelliJ IDEA 或任意 Java IDE

### 2. 运行 Demo

在你的 IDE 中直接运行 `MiniSpring.main()`，控制台将输出：

```
===== 阶段一：开始创建 Bean 实例 =====
创建 Bean: userService (类名: UserService)
创建 Bean: orderservice (类名: OrderService)

===== 阶段二：依赖注入 & 初始化 =====
【注入成功】UserService 的字段 orderService 被注入了 OrderService
【初始化】UserService 的 init() 方法被框架自动调用了！

===== 验证成果 =====
UserService 执行了！
【验证】orderService 已成功注入，地址是：MyLearnSpring.OrderService@15db9742
OrderService 正在执行核心业务逻辑！
```

### 3. 代码示例

```java
@MyComponent("userService")
public class UserService {

    @MyAutowired
    private OrderService orderService;  // 框架会自动注入！

    @MyPostConstruct
    public void init() {
        System.out.println("【初始化】我被框架自动调用了！");
    }

    public void hello() {
        orderService.doSomething();  // 此时 orderService 不为 null
    }
}

@MyComponent
public class OrderService {
    public void doSomething() {
        System.out.println("OrderService 正在执行核心业务逻辑！");
    }
}
```

---

## 🧠 核心原理（三板斧）

MiniSpring 用**两阶段循环**模拟 Spring 的 Bean 生命周期：

| 阶段 | 板斧 | 核心 API | 对应 Spring |
| :--- | :--- | :--- | :--- |
| **第一轮循环** | 第一板斧 | `clazz.getDeclaredConstructor().newInstance()` | 创建 Bean 实例 |
| **第二轮循环（前半段）** | 第三板斧 | `field.setAccessible(true)` + `field.set()` | 依赖注入（`@Autowired`） |
| **第二轮循环（后半段）** | 第二板斧 | `method.setAccessible(true)` + `method.invoke()` | 初始化回调（`@PostConstruct`） |

**关键顺序**：必须先注入依赖，再执行初始化（因为 `init()` 可能用到注入的依赖）。

---

## 📚 我通过这个项目学到了什么

- ✅ 反射的三大核心 API：`newInstance()`、`invoke()`、`set()`
- ✅ 注解的保留策略：`@Retention(RetentionPolicy.RUNTIME)` 让反射能读到注解
- ✅ `setAccessible(true)` 如何打破 `private` 封装
- ✅ IoC 容器本质：就是 `Map<String, Object>`
- ✅ DI 的本质：从 Map 里取出依赖，强行塞进字段里
- ✅ Bean 生命周期的顺序：先创建 → 再注入 → 最后初始化

---

## 🌟 后续拓展方向

- [ ] 支持构造器注入（`@Autowired` 打在构造器上）
- [ ] 支持 `@Value("${xxx}")` 从配置文件读取属性
- [ ] 支持单例/原型作用域（`@Scope("prototype")`）
- [ ] 实现循环依赖处理（**Spring 三级缓存的精髓**）
- [ ] 用 Java Agent 实现 AOP 拦截（`@MyLog` 注解自动打印日志）

## 🤝 关于这个项目

这不是一个生产级框架，而是一个 **「看得懂的框架」** 。

如果你也在学 Java 反射，觉得概念太抽象、代码看不懂，不妨花 30 分钟亲手敲一遍 MiniSpring。你会发现：
> **框架的底层，不过就是 10 个反射 API 在来回调用。**

---

## 📄 License

MIT License — 随便用，随便改，能帮到你就好。

---

**Star 这个仓库** ⭐，让更多同学通过 MiniSpring 走进 Java 底层世界！
