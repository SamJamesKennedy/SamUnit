package samunit.runners;

import java.lang.reflect.Method;
import java.util.List;

import samunit.annotations.TestMethod;

public class TestRunner {

    List<Method> befores;

    public TestRunner(List<Method> befores) {
        this.befores = befores;
    }

    public Result runTest(Object instance, Method method, Class<? extends Throwable> expected) {
        invokeBefores(instance);
        if (expected.isAssignableFrom(TestMethod.NoException.class)) {
            return getResultWithExpected(instance, method, expected);
        } else {
            return getResultWithoutExpected(instance, method);
        }
    }

    private Result getResultWithoutExpected(Object instance, Method method) {
        Result.ResultBuilder builder = new Result.ResultBuilder().withMethod(method);
        try {
            method.invoke(instance, null);
            builder.withStatus(true);
        } catch (Throwable exception) {
            builder.withStatus(false)
                .withException(exception);
        }
        return builder.getInstance();
    }

    private Result getResultWithExpected(Object instance, Method method, Class<? extends Throwable> expected) {
        Result.ResultBuilder builder = new Result.ResultBuilder().withMethod(method);
        try {
            method.invoke(instance, null);
        } catch (Throwable exception) {
            if (exception.getCause().getClass().isAssignableFrom(expected)) {
                builder.withStatus(true);
            } else {
                builder.withStatus(false)
                    .withException(exception.getCause());
            }
            return builder.getInstance();
        }
        return builder.withStatus(true)
                .getInstance();
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
