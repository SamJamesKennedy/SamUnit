package samunit;

import java.io.IOException;

import samunit.runners.SamUnit;

public class Main {

    public static void main(String[] args) {
        SamUnit samUnit = new SamUnit();
        try {
            samUnit.runTests();
        } catch (IOException e) {
            System.out.println("Failed to run tests");
        }
    }

}
