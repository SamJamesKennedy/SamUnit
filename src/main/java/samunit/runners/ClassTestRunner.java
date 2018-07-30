package samunit.runners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import samunit.annotations.TestMethod;

public class ClassTestRunner {

    List<Method> befores;

    ClassTestRunner() {
        befores = new ArrayList<>();
    }

    void setBefores(List<Method> befores) {
        this.befores = befores;
    }

    void clearBefores() {
        befores.clear();
    }

    void runTests(Class<?> classToTest) {
        TestRunner testRunner = new TestRunner(befores);
        Object instance;
        try {
            instance = classToTest.newInstance();
        } catch (Exception e) {
            System.out.println("Failed to run tests for class " + classToTest.getName());
            return;
        }
        List<Result> results = Arrays.stream(classToTest.getMethods())
                .filter(m -> m.isAnnotationPresent(TestMethod.class))
                .map(m -> testRunner.runTest(instance, m, m.getAnnotation(TestMethod.class).expected()))
                .collect(Collectors.toList());

        long numPassed = results.stream().filter(Result::getTestPassed).count();
        long numFailed = results.size() - numPassed;

        results.stream().filter(r -> !r.getTestPassed())
            .map(Result::getException)
            .forEach(Throwable::printStackTrace);

        System.out.println(classToTest.getName() + " Tests complete");
        System.out.println(numPassed + " Tests passed");
        System.out.println(numFailed + " Tests failed");

    }

}
