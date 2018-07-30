package samunit.test;

import samunit.annotations.BeforeEach;
import samunit.annotations.TestClass;
import samunit.annotations.TestMethod;
import samunit.checkers.Check;
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
    public void testPasses() {
        String message = "I am a foo";
        foo.setMessage(message);
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod
    public void testFails() {
        String message = "I am a foo";
        foo.setMessage(message);
        Check.isTrue(foo.getMessage().equals("I am a bar"));
    }

    @TestMethod
    public void testFailBecauseException() {
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod(expected = NullPointerException.class)
    public void testFailsExpectedExceptionNotThrown() {
        String message = "I am a foo";
        foo.setMessage(message);
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod(expected = NullPointerException.class)
    public void testExpectedException() {
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }

    @TestMethod(expected = ArrayIndexOutOfBoundsException.class)
    public void testExpectsOneExceptionAndGetsAnotherFail() {
        String message = "I am a foo";
        Check.isTrue(foo.getMessage().equals(message));
    }
}
