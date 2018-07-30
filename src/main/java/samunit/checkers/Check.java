package samunit.checkers;

public class Check {

    public static void isTrue(boolean b) {
        if (!b) {
            throw new AssertionError("Expected output to be true but was false");
        }
    }

    public static void isFalse(boolean b) {
        if (b) {
            throw new AssertionError("Expected output to be false but was true");
        }
    }

}
