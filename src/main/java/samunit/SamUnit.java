package samunit;

import java.io.IOException;

import samunit.annotations.Test;

public class SamUnit {

    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.runTests();
        } catch (IOException e) {
            System.out.println("Failed to run tests");
        }
    }

}
