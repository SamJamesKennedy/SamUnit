package annotations;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import test.FooTest;

public class UseTestClass {

    public static void main(String[] args) {
        Class<FooTest> fooTestClass = FooTest.class;
        readAnnotation(fooTestClass);
    }

    static void readAnnotation(AnnotatedElement element) {
        Class<?> classToTest = (Class<?>) element;

        for (Method method : classToTest.getMethods()) {
            if (method.isAnnotationPresent(TestMethod.class)) {
                System.out.println("I found a method called " + method.getName() + "!");
            }
        }
    }
}
