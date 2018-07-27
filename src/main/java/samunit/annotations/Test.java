package samunit.annotations;

import java.io.IOException;
import java.lang.reflect.Method;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class Test {

    private ClassTestRunner classTestRunner;

    public Test() {
        classTestRunner = new ClassTestRunner();
    }
    
    public void runTests() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassInfo> classes = classPath.getTopLevelClasses("samunit.test");
        for (ClassInfo classInfo : classes) {
            if (classInfo.load().isAnnotationPresent(TestClass.class)) {
                Class<?> classToTest = classInfo.load();
                testClass(classToTest);
            }
        }
    }

    private void testClass(Class<?> classToTest) {
        if (classToTest.isAnnotationPresent(TestClass.class)) {
            for (Method method : classToTest.getMethods()) {
                if (method.isAnnotationPresent(BeforeEach.class)) {
                    classTestRunner.setBefore(method);
                }
            }
            classTestRunner.runTests(classToTest);
        }
    }

}
