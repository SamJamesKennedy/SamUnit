package test;

import annotations.TestMethod;
import annotations.TestClass;
import foos.Foo;

@TestClass
public class FooTest {

    public Foo foo;

    public FooTest() {
    }

    @TestMethod
    public void testPass() {
        foo = new Foo();
        String message = "I am a foo";
        foo.setMessage(message);
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod
    public void testFail() {
        foo = new Foo();
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod(expected = NullPointerException.class)
    public void testExpectedException() {
        foo = new Foo();
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }
}
