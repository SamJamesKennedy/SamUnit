package samunit.annotations;

import java.lang.reflect.Method;

public class ClassTestRunner {

    Method before;

    public ClassTestRunner() {

    }

    public void setBefore(Method before) {
        this.before = before;
    }

    public void runTests(Class<?> classToTest) {
        Object instance;
        try {
            instance = classToTest.newInstance();
        } catch (Exception e) {
            System.out.println("Failed to run tests for class " + classToTest.getName());
            return;
        }
        int numPassed = 0;
        int numFailed = 0;
        for (Method method : classToTest.getMethods()) {
            if (method.isAnnotationPresent(TestMethod.class)) {
                TestMethod annotation = method.getAnnotation(TestMethod.class);
                boolean testPassed = runTest(instance, method, annotation.expected());
                if (testPassed) {
                    numPassed++;
                } else {
                    numFailed++;
                }
            }
        }
        System.out.println(classToTest.getName() + " Tests complete");
        System.out.println(numPassed + " Tests passed");
        System.out.println(numFailed + " Tests failed");

    }

    private boolean runTest(Object instance, Method method, Class<? extends Throwable> expected) {
        try {
            if (before != null) {
                before.invoke(instance, null);
            }
            method.invoke(instance, null);
        } catch (Throwable exception) {
            if (exception.getCause().getClass().isAssignableFrom(expected)) {
                return true;
            } else {
                exception.getCause().printStackTrace();
                return false;
            }
        }
        return true;
    }



}
