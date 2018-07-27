package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestMethod {

    class NoException extends Throwable {
        private NoException() {

        }
    }

    Class<? extends Throwable> expected() default NoException.class;
}
