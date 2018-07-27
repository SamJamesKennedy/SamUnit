package annotations;

import java.lang.reflect.Method;

public class TestRunner {

    public static void runTest(Class c, Method method, Class<? extends Throwable> expected)
            throws IllegalAccessException, InstantiationException {
        Object o = c.newInstance();
        try {
            method.invoke(o, null);
        } catch (Throwable exception) {
            if (exception.getCause().getClass().getName().equals(expected.getName())) {
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
