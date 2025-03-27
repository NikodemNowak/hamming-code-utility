package org.zespol;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        KodHamminga kodHamminga = new KodHamminga();
        boolean[] message = {true, false, true, false, true, false, true, false};
        boolean[] code = kodHamminga.messageToCode1(message);
        System.out.println("Wiadomość: " + Arrays.toString(message));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        code[11] = !code[11]; // Wprowadzenie błędu
        kodHamminga.verifyCode(code);
    }
}
