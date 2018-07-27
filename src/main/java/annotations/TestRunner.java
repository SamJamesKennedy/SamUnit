package annotations;

import java.lang.reflect.Method;

public class TestRunner {

    public static void runTest(Class c, Object instance, Method method, Class<? extends Throwable> expected) {
        try {
            method.invoke(instance, null);
        } catch (Throwable exception) {
            if (exception.getCause().getClass().isAssignableFrom(expected)) {
                passTest(method);
                return;
            } else {
                failTest(method);
                return;
            }
        }
        passTest(method);
    }

    private static void passTest(Method method) {
        System.out.println("Test " + method.getName() + " passed!");
    }

    private static void failTest(Method method) {
        System.out.println("Test " + method.getName() + " failed!");
    }
}
