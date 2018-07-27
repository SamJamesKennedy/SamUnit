package samunit.checkers;

public class Check {

    public static void isTrue(boolean b) throws CheckError {
        if (!b) {
            throw new CheckError("Expected output to be true but was false");
        }
    }

    public static void isFalse(boolean b) throws CheckError {
        if (!b) {
            throw new CheckError("Expected output to be false but was true");
        }
    }

}
