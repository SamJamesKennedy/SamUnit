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

    @BeforeEach(priority = 0)
    public void setup() {
        bar = new Bar();
    }

    @BeforeEach(priority = 1)
    public void setMessage() {
        bar.setMessage("I am a bar");
    }

    @TestMethod
    public void testPasses() {
        Check.isTrue(bar.getMessage().equals("I am a bar"));
    }

    @TestMethod
    public void testFails() {
        Check.isTrue(bar.getMessage().equals("I am a foo"));
    }
}
