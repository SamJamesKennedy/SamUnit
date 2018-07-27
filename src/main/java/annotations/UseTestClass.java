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
            if (classInfo.load().isAnnotationPresent(TestClass.class)) {
                Class<?> classToTest = classInfo.load();
                testClass(classToTest);
            }
        }
    }

    private static void testClass(Class<?> classToTest) {
        if (classToTest.isAnnotationPresent(TestClass.class)) {
            Object instance = null;
            for (Method method : classToTest.getMethods()) {
                if(method.isAnnotationPresent(Setup.class)) {
                    instance = setupClass(classToTest, method);
                    break;
                }
            }
            if (instance == null) {
                try {
                    instance = classToTest.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            runTests(classToTest, instance);
        }
    }

    private static Object setupClass(Class<?> classToTest, Method initialisation) {
        try {
            Object obj = classToTest.newInstance();
            initialisation.invoke(obj,null);
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    static void runTests(Class<?> classToTest, Object instance) {
        for (Method method : classToTest.getMethods()) {
            if (method.isAnnotationPresent(TestMethod.class)) {
                TestMethod annotation = method.getAnnotation(TestMethod.class);
                TestRunner.runTest(classToTest, instance, method, annotation.expected());
            }
        }
    }
}
