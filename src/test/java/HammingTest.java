import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zespol.HammingCode;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingTest {

    HammingCode hamming;
    boolean[] message1;
    boolean[] message2;
    boolean[] message3;
    boolean[] message4;
    boolean[] message5;
    boolean[] message6;

    @BeforeEach
    public void setUp() {
        hamming = new HammingCode();
        message1 = new boolean[] {true, false, true, false, true, false, true, false};
        message2 = new boolean[] {false, true, false, true, false, true, false, true};
        message3 = new boolean[] {true, true, false, false, true, true, false, false};
        message4 = new boolean[] {false, false, true, true, false, false, true, true};
        message5 = new boolean[] {true, false, false, true, true, false, false, true};
        message6 = new boolean[] {false, true, true, false, true, true, false, false};
    }

    @Test
    public void testMessage1ToCode1Bit() {
        System.out.println("\n\nPrzykład 1 dla podstawowego kodu Hamminga (12,8): ");
        boolean[] code = hamming.messageToCode1Bit(message1);
        System.out.println("Wiadomość: " + Arrays.toString(message1));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        boolean[] brokenCode = code.clone();
        brokenCode[11] = !brokenCode[11];
        System.out.println("Kod z błędem: " + Arrays.toString(brokenCode));
        boolean[] syndrome = hamming.calculateSyndrome1Bit(brokenCode);
        int brokenBit = hamming.whichBitBroken1Bit(syndrome);
        boolean[] repairedCode = new boolean[brokenCode.length];
        if (brokenBit != -1) {
            System.out.println("Uszkodzony bit: " + brokenBit);
            repairedCode = hamming.repairBit1Bit(brokenCode, brokenBit);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(repairedCode));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        assertEquals(code, repairedCode);
    }

    @Test
    public void testMessage2ToCode1Bit() {
        System.out.println("\n\nPrzykład 2 dla podstawowego kodu Hamminga (12,8): ");
        boolean[] code = hamming.messageToCode1Bit(message2);
        System.out.println("Wiadomość: " + Arrays.toString(message2));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        boolean[] brokenCode = code.clone();
        brokenCode[7] = !brokenCode[7];
        System.out.println("Kod z błędem: " + Arrays.toString(brokenCode));
        boolean[] syndrome = hamming.calculateSyndrome1Bit(brokenCode);
        int brokenBit = hamming.whichBitBroken1Bit(syndrome);
        boolean[] repairedCode = new boolean[brokenCode.length];
        if (brokenBit != -1) {
            System.out.println("Uszkodzony bit: " + brokenBit);
            repairedCode = hamming.repairBit1Bit(brokenCode, brokenBit);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(repairedCode));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        assertEquals(code, repairedCode);
    }

    @Test
    public void testMessage3ToCode1Bit() {
        System.out.println("\n\nPrzykład 3 dla podstawowego kodu Hamminga (12,8): ");
        boolean[] code = hamming.messageToCode1Bit(message3);
        System.out.println("Wiadomość: " + Arrays.toString(message3));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        System.out.println("Nic nie zmieniamy");
        boolean[] brokenCode = code.clone();
        boolean[] syndrome = hamming.calculateSyndrome1Bit(brokenCode);
        int brokenBit = hamming.whichBitBroken1Bit(syndrome);
        boolean[] repairedCode = new boolean[brokenCode.length];
        if (brokenBit != -1) {
            System.out.println("Uszkodzony bit: " + brokenBit);
            repairedCode = hamming.repairBit1Bit(brokenCode, brokenBit);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(repairedCode));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        assertEquals(code, repairedCode);
    }

    @Test
    public void testMessage4ToCode1Bit() {
        System.out.println("\n\nPrzykład 1 dla podstawowego kodu Hamminga (16,8): ");
        boolean [] code = hamming.messageToCode2Bits(message4);
        System.out.println("Wiadomość: " + Arrays.toString(message4));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        System.out.println("Nic nie zmieniamy");
        boolean[] brokenCode = code.clone();
        boolean[] syndrome = hamming.calculateSyndrome2Bits(brokenCode);
        int[] brokenBits = hamming.whichBitBroken2Bit(syndrome);
        boolean[] repairedCode = new boolean[brokenCode.length];
        if (brokenBits[0] != -1) {
            System.out.println("Uszkodzony bit: " + Arrays.toString(brokenBits));
            repairedCode = hamming.repairBit2Bit(brokenCode, brokenBits);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(repairedCode));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        assertEquals(code, repairedCode);
    }

    @Test
    public void testMessage5(){
        System.out.println("\n\nPrzykład 2 dla podstawowego kodu Hamminga (16,8): ");
        boolean [] code = hamming.messageToCode2Bits(message5);
        System.out.println("Wiadomość: " + Arrays.toString(message5));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        boolean[] brokenCode = code.clone();
        brokenCode[11] = !brokenCode[11];
        System.out.println("Kod z błędem: " + Arrays.toString(brokenCode));
        boolean[] syndrome = hamming.calculateSyndrome2Bits(brokenCode);
        int[] brokenBits = hamming.whichBitBroken2Bit(syndrome);
        boolean[] repairedCode = new boolean[brokenCode.length];
        if (brokenBits[0] != -1) {
            System.out.println("Uszkodzony bit: " + Arrays.toString(brokenBits));
            repairedCode = hamming.repairBit2Bit(brokenCode, brokenBits);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(repairedCode));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        assertEquals(code, repairedCode);
    }

    @Test
    public void testMessage6(){
        System.out.println("\n\nPrzykład 3 dla podstawowego kodu Hamminga (16,8): ");
        boolean [] code = hamming.messageToCode2Bits(message6);
        System.out.println("Wiadomość: " + Arrays.toString(message6));
        System.out.println("Kod Hamminga: " + Arrays.toString(code));
        boolean[] brokenCode = code.clone();
        brokenCode[1] = !brokenCode[1];
        brokenCode[3] = !brokenCode[3];
        System.out.println("Kod z błędem: " + Arrays.toString(brokenCode));
        boolean[] syndrome = hamming.calculateSyndrome2Bits(brokenCode);
        int[] brokenBits = hamming.whichBitBroken2Bit(syndrome);
        boolean[] repairedCode = new boolean[brokenCode.length];
        if (brokenBits[0] != -1) {
            System.out.println("Uszkodzony bit: " + Arrays.toString(brokenBits));
            repairedCode = hamming.repairBit2Bit(brokenCode, brokenBits);
            System.out.println("Kod po poprawieniu błędu: " + Arrays.toString(repairedCode));
        } else {
            System.out.println("Brak uszkodzonego bitu.");
        }
        assertEquals(code, repairedCode);
    }

}
