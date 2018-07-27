package test;

import annotations.TestMethod;
import annotations.TestClass;

@TestClass
public class FooTest {

    public FooTest() {

    }

    @TestMethod
    public void testPass() {

    }

    @TestMethod
    public void testFail() {

    }

    @TestMethod(expected = NullPointerException.class)
    public void testExpectedException() {

    }
}
