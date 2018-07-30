package samunit.runners;

import java.lang.reflect.Method;
import java.util.List;

import samunit.annotations.TestMethod;

class TestRunner {

    List<Method> befores;

    TestRunner(List<Method> befores) {
        this.befores = befores;
    }

    Result runTest(Object instance, Method method, Class<? extends Throwable> expected) {
        invokeBefores(instance);
        if (expected.isAssignableFrom(TestMethod.NoException.class)) {
            return getResultWithoutExpected(instance, method);
        } else {
            return getResultWithExpected(instance, method, expected);
        }
    }

    private Result getResultWithoutExpected(Object instance, Method method) {
        Result.ResultBuilder builder = new Result.ResultBuilder().withMethod(method);
        try {
            method.invoke(instance, null);
            builder.withTestPassed(true);
        } catch (Throwable exception) {
            builder.withTestPassed(false)
                .withException(exception.getCause());
        }
        return builder.getInstance();
    }

    private Result getResultWithExpected(Object instance, Method method, Class<? extends Throwable> expected) {
        Result.ResultBuilder builder = new Result.ResultBuilder().withMethod(method);
        try {
            method.invoke(instance, null);
            String message = "Expected error: " + expected.getName() + " but no error was thrown.";
            return builder.withTestPassed(false)
                    .withException(new AssertionError(message))
                    .getInstance();
        } catch (Throwable exception) {
            if (exception.getCause().getClass().isAssignableFrom(expected)) {
                builder.withTestPassed(true);
            } else {
                builder.withTestPassed(false)
                    .withException(exception.getCause());
            }
            return builder.getInstance();
        }
    }

    private void invokeBefores(Object instance) {
        for (Method before : befores) {
            try {
                before.invoke(instance, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
