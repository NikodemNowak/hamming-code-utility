package org.zespol;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HammingCode hamming = new HammingCode();

        System.out.println("Przykłady dla podstawowego kodu Hamminga (12,8): ");

        // Przykładowa wiadomość do zakodowania 1
        boolean[] message = {true, false, true, false, true, false, true, false};
        boolean[] code = hamming.messageToCode1Bit(message);
        System.out.println("Wiadomość: " + Arrays.toString(message));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        code[11] = !code[11]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code));

        hamming.whichBitBroken1Bit(hamming.calculateSyndrome1Bit(code));

        System.out.println("----------------------------------");

        // Przykładowa wiadomość do zakodowania 2
        boolean[] message2 = {false, true, false, true, false, true, false, true};
        boolean[] code2 = hamming.messageToCode1Bit(message2);
        System.out.println("Wiadomość: " + Arrays.toString(message2));
        System.out.println("Kod Hamminga: " + Arrays.toString(code2));
        code2[7] = !code2[7]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code2));
        hamming.whichBitBroken1Bit(hamming.calculateSyndrome1Bit(code2));

        System.out.println("----------------------------------");

        // Przykładowa wiadomość do zakodowania 3
        boolean[] message3 = {true, true, false, false, true, true, false, false};
        boolean[] code3 = hamming.messageToCode1Bit(message3);
        System.out.println("Wiadomość: " + Arrays.toString(message3));
        System.out.println("Kod Hamminga: " + Arrays.toString(code3));
        code3[2] = !code3[2]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code3));
        hamming.whichBitBroken1Bit(hamming.calculateSyndrome1Bit(code3));

        System.out.println("----------------------------------");

        System.out.println("Przykłady dla rozszerzonego kodu Hamminga (13,8): ");

        // Przykładowa wiadomość do zakodowania 4
        boolean[] message4 = {true, false, false, false, false, false, false, false};
        boolean[] code4 = hamming.messageToCode2Bits(message4);
        System.out.println("Wiadomość: " + Arrays.toString(message4));
        System.out.println("Kod Hamminga: " + Arrays.toString(code4));
        hamming.calculateSyndrome2Bits(code4);
    }
}
