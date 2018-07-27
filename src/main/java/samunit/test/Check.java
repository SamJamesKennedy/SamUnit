package samunit.test;

public class Check {

    static void isTrue(boolean b) throws CheckError {
        if (!b) {
            throw new CheckError("Expected output to be true but was false");
        }
    }

    static void isFalse(boolean b) throws CheckError {
        if (!b) {
            throw new CheckError("Expected output to be false but was false");
        }
    }

}
