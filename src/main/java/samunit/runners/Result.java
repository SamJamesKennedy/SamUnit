package samunit.runners;

import java.lang.reflect.Method;

public class Result {

    private boolean testPassed;
    private Method method;
    private Throwable exception;

    private Result() {

    }

    public boolean getTestPassed() {
        return testPassed;
    }

    public void setTestPassed(boolean testPassed) {
        this.testPassed = testPassed;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public static class ResultBuilder {

        Result result;

        public ResultBuilder() {
            result = new Result();
        }

        public Result build() {
            return result;
        }

        public ResultBuilder withTestPassed(boolean testPassed) {
            result.setTestPassed(testPassed);
            return this;
        }

        public ResultBuilder withMethod(Method method) {
            result.setMethod(method);
            return this;
        }

        public ResultBuilder withException(Throwable exception) {
            result.setException(exception);
            return this;
        }

    }
}
