package samunit.runners;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import samunit.annotations.BeforeEach;
import samunit.annotations.TestClass;
import samunit.annotations.TestMethod;

public class SamUnit {

    private ClassTestRunner classTestRunner;

    public SamUnit() {
        classTestRunner = new ClassTestRunner();
    }
    
    public void runTests() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassInfo> classes = classPath.getTopLevelClasses("samunit.test");
        classes.stream().map(ClassInfo::load)
            .filter(c -> c.isAnnotationPresent(TestClass.class))
            .forEach(this::testClass);
    }

    private void testClass(Class<?> classToTest) {
        classTestRunner.clearBefores();
        List<Method> befores = Arrays.stream(classToTest.getMethods())
            .filter(m -> m.isAnnotationPresent(BeforeEach.class))
            .collect(Collectors.toList());
        classTestRunner.setBefores(befores);
        classTestRunner.runTests(classToTest);
    }
}
