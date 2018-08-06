package samunit.runners;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import samunit.annotations.BeforeEach;
import samunit.annotations.TestClass;

public class SamUnit {

    private static ClassTestRunner classTestRunner = new ClassTestRunner();
    
    public static void main(String[] args) throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassInfo> classes = classPath.getTopLevelClasses("samunit.test");
        classes.stream().map(ClassInfo::load)
            .filter(c -> c.isAnnotationPresent(TestClass.class))
            .forEach(SamUnit::testClass);
    }

    private static void testClass(Class<?> classToTest) {
        classTestRunner.clearBefores();
        List<Method> befores = Arrays.stream(classToTest.getMethods())
            .filter(m -> m.isAnnotationPresent(BeforeEach.class))
            .sorted(Comparator.comparing(SamUnit::getMethodPriority))
            .collect(Collectors.toList());
        classTestRunner.setBefores(befores);
        classTestRunner.runTests(classToTest);
    }

    private static Integer getMethodPriority(Method m) {
        return m.getAnnotation(BeforeEach.class).priority();
    }
}
