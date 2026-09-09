package MyLearnSpring;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

// ① 必须加上 Retention，否则反射在运行时找不到它！
@Retention(RetentionPolicy.RUNTIME)
// ② 限定这个注解只能贴在“类”上面
@Target(ElementType.TYPE)
public @interface MyComponent {
    // ③ 这就是你代码里调用的 annotation.value()
    String value() default "";
}