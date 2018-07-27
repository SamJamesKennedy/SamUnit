package annotations;

import java.lang.reflect.Method;

public class TestRunner {

    public static void runTest(Method method) {
        Class<? extends Throwable> expected = method.getAnnotation(TestMethod.class).expected();
    }
}
