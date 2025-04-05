package org.zespol;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HammingCode hamming = new HammingCode();

        // Przykładowa wiadomość do zakodowania 1
        boolean[] message = {true, false, true, false, true, false, true, false};
        boolean[] code = hamming.messageToCode1(message);
        System.out.println("Wiadomość: " + Arrays.toString(message));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        code[11] = !code[11]; // Wprowadzenie błędu
        hamming.whichBitBroken(hamming.verifyCode(code));

        // Przykładowa wiadomość do zakodowania 2
        boolean[] message2 = {false, true, false, true, false, true, false, true};
        boolean[] code2 = hamming.messageToCode1(message2);
        System.out.println("Wiadomość: " + Arrays.toString(message2));
        System.out.println("Kod Hamminga: " + Arrays.toString(code2));
        code2[7] = !code2[7]; // Wprowadzenie błędu
        hamming.whichBitBroken(hamming.verifyCode(code2));
    }
}
