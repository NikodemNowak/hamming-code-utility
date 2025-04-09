package org.zespol;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        HammingCode hamming = new HammingCode();

        System.out.println("\n\nPrzykłady dla podstawowego kodu Hamminga (12,8): ");

        // Przykładowa wiadomość do zakodowania 1
        boolean[] message = {true, false, true, false, true, false, true, false};
        boolean[] code = hamming.messageToCode1Bit(message);
        System.out.println("Wiadomość: " + Arrays.toString(message));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        code[11] = !code[11]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code));
        boolean[] syndrome1 = hamming.calculateSyndrome1Bit(code);
        System.out.println("Syndrom: " + Arrays.toString(syndrome1));
        int brokenBit = hamming.whichBitBroken1Bit(syndrome1);
        if (brokenBit != -1) {
            System.out.println("Uszkodzony bit: " + brokenBit);
            code = hamming.repairBit1Bit(code, brokenBit);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(code));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }

        System.out.println("----------------------------------");



        // Przykładowa wiadomość do zakodowania 2
        boolean[] message2 = {false, true, false, true, false, true, false, true};
        boolean[] code2 = hamming.messageToCode1Bit(message2);
        System.out.println("Wiadomość: " + Arrays.toString(message2));
        System.out.println("Kod Hamminga: " + Arrays.toString(code2));
        code2[7] = !code2[7]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code2));
        boolean [] syndrome2 = hamming.calculateSyndrome1Bit(code2);
        System.out.println("Syndrom: " + Arrays.toString(syndrome2));
        int brokenBit2 = hamming.whichBitBroken1Bit(syndrome2);
        if (brokenBit2 != -1) {
            System.out.println("Uszkodzony bit: " + brokenBit2);
            code2 = hamming.repairBit1Bit(code2, brokenBit2);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(code2));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }




        System.out.println("----------------------------------");

        // Przykładowa wiadomość do zakodowania 3
        boolean[] message3 = {true, true, false, false, true, true, false, false};
        boolean[] code3 = hamming.messageToCode1Bit(message3);
        System.out.println("Wiadomość: " + Arrays.toString(message3));
        System.out.println("Kod Hamminga: " + Arrays.toString(code3));
        boolean[] syndrome3 = hamming.calculateSyndrome1Bit(code3);
        System.out.println("Syndrom: " + Arrays.toString(syndrome3));
        int brokenBit3 = hamming.whichBitBroken1Bit(syndrome3);
        if (brokenBit3 != -1) {
            System.out.println("Uszkodzony bit: " + brokenBit3);
            code3 = hamming.repairBit1Bit(code3, brokenBit3);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(code3));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        System.out.println("----------------------------------");




        System.out.println("\n\nPrzykłady dla rozszerzonego kodu Hamminga (16,8): ");

        // Przykładowa wiadomość do zakodowania 4
        boolean[] message4 = {true, false, false, false, false, false, false, false};
        boolean[] code4 = hamming.messageToCode2Bits(message4);
        System.out.println("Wiadomość: " + Arrays.toString(message4));
        System.out.println("Kod Hamminga: " + Arrays.toString(code4));
        boolean[] syndrome4 = hamming.calculateSyndrome2Bits(code4);
        System.out.println("Syndrom: " + Arrays.toString(syndrome4));
        int[] brokenBit4 = hamming.whichBitBroken2Bit(syndrome4);
        boolean isBroken4 = false;
        for(int bit : brokenBit4) {
            if (bit == -1) {
                System.out.println("Brak uszkodzonego bitu.");
            } else {
                System.out.println("Uszkodzony bit: " + bit);
                isBroken4 = true;
            }
        }
        if (isBroken4) {
            code4 = hamming.repairBit2Bit(code4, brokenBit4);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(code4));
        }



        System.out.println("----------------------------------");
        // Przykładowa wiadomość do zakodowania 5
        boolean[] message5 = {true, true, false, false, false, false, false, false};
        boolean[] code5 = hamming.messageToCode2Bits(message5);
        System.out.println("Wiadomość: " + Arrays.toString(message5));
        System.out.println("Kod Hamminga: " + Arrays.toString(code5));
        code5[11] = !code5[11]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code5));
        boolean[] syndrome5 = hamming.calculateSyndrome2Bits(code5);
        System.out.println("Syndrom: " + Arrays.toString(syndrome5));
        int[] brokenBit5 = hamming.whichBitBroken2Bit(syndrome5);
        boolean isBroken5 = false;
        for(int bit : brokenBit5) {
            if (bit == -1) {
                System.out.println("Brak uszkodzonego bitu.");
            } else {
                System.out.println("Uszkodzony bit: " + bit);
                isBroken5 = true;
            }
        }
        if (isBroken5) {
            code5 = hamming.repairBit2Bit(code5, brokenBit5);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(code5));
        }



        System.out.println("----------------------------------");
        // Przykładowa wiadomość do zakodowania 6
        boolean[] message6 = {true, true, false, false, true, true, false, false};
        boolean[] code6 = hamming.messageToCode2Bits(message6);
        System.out.println("Wiadomość: " + Arrays.toString(message6));
        System.out.println("Kod Hamminga: " + Arrays.toString(code6));
        code6[1] = !code6[1]; // Wprowadzenie błędu
        code6[3] = !code6[3]; // Wprowadzenie błędu
        System.out.println("Kod z błędem: " + Arrays.toString(code6));
        boolean[] syndrome6 = hamming.calculateSyndrome2Bits(code6);
        System.out.println("Syndrom: " + Arrays.toString(syndrome6));
        int[] brokenBit6 = hamming.whichBitBroken2Bit(syndrome6);
        boolean isBroken6 = false;
        for(int bit : brokenBit6) {
            if (bit == -1) {
                System.out.println("Brak uszkodzonego bitu.");
            } else {
                System.out.println("Uszkodzony bit: " + bit);
                isBroken6 = true;
            }
        }
        if (isBroken6) {
            code6 = hamming.repairBit2Bit(code6, brokenBit6);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(code6));
        }
    }
}
