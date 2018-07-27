package samunit.test;

import samunit.annotations.BeforeEach;
import samunit.annotations.TestClass;
import samunit.annotations.TestMethod;
import samunit.foos.Foo;

@TestClass
public class FooTest {

    public Foo foo;

    public FooTest() {
    }

    @BeforeEach
    public void setup() {
        foo = new Foo();
    }

    @TestMethod
    public void testPass() {
        String message = "I am a foo";
        foo.setMessage(message);
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod
    public void testFail() {
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod(expected = NullPointerException.class)
    public void testExpectedException() {
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }
}
