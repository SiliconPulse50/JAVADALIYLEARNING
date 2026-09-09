package MyLearnSpring;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)  // 必须加，让反射能读到
@Target(ElementType.METHOD)          // 这个注解只能贴在方法上
public @interface MyInit {
    // 不需要任何属性，就是个标记
}