package annotations;

import java.io.IOException;
import java.lang.reflect.Method;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public class UseTestClass {

    public static void main(String[] args) throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassInfo> classes = classPath.getTopLevelClasses("test");
        for (ClassInfo classInfo : classes) {
            Class<?> classToTest = classInfo.load();
            if (classToTest.isAnnotationPresent(TestClass.class)) {
                runTests(classToTest);
            }
        }
    }

    static void runTests(Class<?> classToTest) {
        for (Method method : classToTest.getMethods()) {
            if (method.isAnnotationPresent(TestMethod.class)) {
                TestMethod annotation = method.getAnnotation(TestMethod.class);
                try {
                    TestRunner.runTest(classToTest, method, annotation.expected());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
