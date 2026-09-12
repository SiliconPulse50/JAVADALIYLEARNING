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

## 我的收获
一、你说的完全对，这就是软件开发的**两层世界**
层次	是什么	谁在干
底层原理	反射、注解、动态代理、IoC、AOP	你自己手写 MiniSpring
上层框架	Spring 封装好的 @Service、@Autowired、@Transactional	直接用注解写业务
框架 = 底层原理的“封装成品”。

二、用你亲手写过的代码，一一对应
你写的（底层）	Spring 的（上层）	关系
@MyComponent + newInstance()	@Service / @Component	Spring 把“扫描+创建”封装好了
@MyAutowired + field.set()	@Autowired	Spring 把“找依赖+注入”封装好了
@MyInit + method.invoke()	@PostConstruct	Spring 把“初始化回调”封装好了
@MyLog + Proxy.newProxyInstance()	@Transactional / @Aspect	Spring 把“代理+拦截”封装好了
Map<String, Object> container	ApplicationContext	Spring 把容器做大了（支持父子容器、懒加载、作用域等）
你写的每一行，Spring 里都有对应的成品。只是它做得更完善、更通用、更稳定。

三、那为什么还要学底层？
因为只会用框架的人，和懂底层的人，遇到问题时的反应完全不同。

场景 1：@Transactional 失效了
只会用框架的人：百度“@Transactional 失效”，抄一堆解决方案，但不知道为什么

懂底层的人：立刻想到“是不是同类内部调用了 this？”，直接定位问题

场景 2：想扩展功能
只会用框架的人：等着 Spring 出新版本

懂底层的人：自己写一个 @MyCache 注解 + AOP 拦截，实现缓存功能

场景 3：面试被问原理
只会用框架的人：“就是加个注解就行了……”

懂底层的人：“@Autowired 底层是反射 field.set()，Spring 启动时扫描注解、从容器里找依赖、用 setAccessible(true) 突破私有字段……”

这就是“会用”和“懂”的差距。

四、实际开发中怎么选？
大多数情况：用 Spring 封装好的
java
@Service
public class OrderService {
    @Autowired
    private StockService stockService;

    @Transactional
    public void createOrder() { ... }
}
因为你没必要重复造轮子。 Spring 已经把这些功能做得很稳了。

特殊情况：自己封装
公司内部需要一个特殊的监控注解（Spring 没有）

需要一个自定义的权限校验注解（业务特有）

需要改 Spring 某个功能的行为（读懂底层才能改）

这时候你手写 MiniSpring 的经验就派上用场了——你知道该从哪下手。

五、用一个比喻总结
底层原理 = 发动机原理。
上层框架 = 一辆造好的汽车。

平时开车，你只需要踩油门、打方向盘（用 Spring 注解）

但车坏了、想改装、想造新车，你必须懂发动机原理（反射、代理、IoC、AOP）

你这两天的学习，就是从“学开车”跳到了“学发动机”。

六、你现在的知识地图
text
【底层原理】（你已经亲手实践过）
├── 反射（Class、Method、Field）
├── 注解（@Retention、@Target、@interface）
├── IoC（创建对象、注入依赖）
├── AOP（动态代理、拦截器）
└── Bean 生命周期（创建 → 注入 → 初始化 → 代理）
        │
        │ 被 Spring 封装成
        ↓
【上层框架】（你平时用的）
├── @Service / @Component
├── @Autowired
├── @PostConstruct
├── @Transactional
└── @Aspect / @Before / @Around
