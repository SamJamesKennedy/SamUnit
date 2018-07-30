package samunit.test;

import samunit.annotations.BeforeEach;
import samunit.annotations.TestClass;
import samunit.annotations.TestMethod;
import samunit.checkers.Check;
import samunit.foos.Bar;

@TestClass
public class BarTest {

    public Bar bar;

    public BarTest() {
    }

    @BeforeEach
    public void setup() {
        bar = new Bar();
    }

    @TestMethod
    public void testPasses() {
        String message = "I am a bar";
        bar.setMessage(message);
        Check.isTrue(bar.getMessage().equals(message));
    }

    @TestMethod
    public void testFails() {
        String message = "I am a bar";
        bar.setMessage(message);
        Check.isTrue(bar.getMessage().equals("I am a foo"));
    }
}
