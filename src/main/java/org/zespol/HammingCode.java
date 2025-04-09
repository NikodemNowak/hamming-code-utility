package org.zespol;

import java.util.Arrays;

public class HammingCode {

    boolean[][] parityBitsControlMatrix1Bit = {{
        true, false, true, false, true, false, true, false, true, false, true, false
    }, {
        false, true, true, false, false, true, true, false, false, true, true, false
    }, {
        false, false, false, true, true, true, true, false, false, false, false, true
    }, {
        false, false, false, false ,false, false, false, true, true, true, true, true
    }};

    public boolean[] messageToCode1Bit(boolean[] message) {
        boolean[] code = new boolean[12];
        int j = 0;
        for (int i = 0; i < code.length; i++) {
            if (i == 0 || i == 1 || i == 3 || i == 7) {
                continue;
            }
            code[i] = message[j];
            j++;
        }

        code[0] = code[2] ^ code[4] ^ code[6] ^ code[8] ^ code[10];
        code[1] = code[2] ^ code[5] ^ code[6] ^ code[9] ^ code[10];
        code[3] = code[4] ^ code[5] ^ code[6] ^ code[11];
        code[7] = code[8] ^ code[9] ^ code[10] ^ code[11];

        return code;
    }

    public boolean[] calculateSyndrome1Bit(boolean[] code) {
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix1Bit);
        boolean[] syndrome = new boolean[4];
        boolean[][] temp = new boolean[code.length][transposedMatrix[0].length];

        for (int i=0; i<code.length; i++) {
            if (code[i]) {
                System.arraycopy(transposedMatrix[i], 0, temp[i], 0, transposedMatrix[i].length);
            }
        }

        for (boolean[] booleans : temp) {
            for (int j = 0; j < booleans.length; j++) {
                syndrome[j] ^= booleans[j];
            }
        }

        return syndrome;
    }

    public int whichBitBroken1Bit(boolean[] resultCode){
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix1Bit);
        for(int i=0; i<transposedMatrix.length; i++){
            boolean[] row = transposedMatrix[i];
            boolean isBroekn = true; // Zakładamy, że bit kodu jest uszkodzony
            for(int j=0; j<row.length; j++){
                if(row[j] != resultCode[j]){
                    isBroekn = false;   // Jeśli chociaż 1 bit wiersza różni się od syndromu tzn, że nie ten bit kodu jest uszkodzony
                    break;
                }
            }
            if(isBroekn){
                return (i+1); // Zwracamy numer uszkodzonego bitu
            }
        }
        return -1; // Jeśli nie znaleziono uszkodzonego bitu, zwracamy -1
    }

    public boolean[] repairBit1Bit(boolean[] code, int brokenBit) {
        brokenBit--; // Zmniejszamy o 1, aby uzyskać indeks tablicy
        if (brokenBit >= 0 && brokenBit < code.length) {
            code[brokenBit] = !code[brokenBit]; // Naprawiamy uszkodzony bit
        }
        return code;
    }

    public static boolean[][] transposeMatrix(boolean[][] matrix){
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] transposedMatrix = new boolean[n][m];

        for(int x = 0; x < n; x++) {
            for(int y = 0; y < m; y++) {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }

        return transposedMatrix;
    }

    boolean[][] parityBitsControlMatrix2Bits = {
            {true, false, true, false, true, false, true, false,    true, false, false, false, false, false, false, false},
            {false, true, true, false, false, true, true, false,    false, true, false, false, false, false, false, false},
            {false, false, false, true, true, true, true, false,    false, false, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false},
            {true, true, false, false, false, false, false, false,  false, false, false, false, true, false, false, false},
            {false, false, true, true, false, false, false, false,  false, false, false, false, false, true, false, false},
            {false, false, false, false, true, true, false, false,  false, false, false, false, false, false, true, false},
            {false, false, false, false, false, false, true, true,  false, false, false, false, false, false, false, true}
    };

    public boolean[] messageToCode2Bits(boolean[] message) {
        boolean[] code = new boolean[16];

        System.arraycopy(message, 0, code, 0, message.length);

        code[8] = code[0] ^ code[2] ^ code[4] ^ code[6];
        code [9] = code[1] ^ code[2] ^ code[5] ^ code[6];
        code[10] = code[3] ^ code[4] ^ code[5] ^ code[6];
        code[11] = code[7];
        code[12] = code[0] ^ code[1];
        code[13] = code[2] ^ code[3];
        code[14] = code[4] ^ code[5];
        code[15] = code[6] ^ code[7];

        return code;
    }

    public boolean[] calculateSyndrome2Bits(boolean[] code) { // Lepsza nazwa funkcji
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix2Bits);
        boolean[] syndrome = new boolean[8];
        boolean[][] temp = new boolean[code.length][transposedMatrix[0].length];

        for (int i=0; i<code.length; i++) {
            if (code[i]) {
                System.arraycopy(transposedMatrix[i], 0, temp[i], 0, transposedMatrix[i].length);
            }
        }

        for (boolean[] booleans : temp) {
            for (int j = 0; j < booleans.length; j++) {
                syndrome[j] ^= booleans[j];
            }
        }

        return syndrome;
    }

    public int[] whichBitBroken2Bit(boolean[] syndrome){
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix2Bits);
        for(int i=0; i<transposedMatrix.length; i++){
            boolean[] row = transposedMatrix[i];
            boolean isBroekn = true;
            for(int j=0; j<row.length; j++){
                if(row[j] != syndrome[j]){
                    isBroekn = false;
                    break;
                }
            }
            if(isBroekn){
                return new int[]{i+1}; // Zwracamy numer uszkodzonego bitu
            }
        }

        // Sprawdzamy kombinacje 2 bitów
        // Dla dwóch uszkodzonych bitów, syndrom będzie XORem odpowiednich wierszy macierzy
        for (int i = 0; i < transposedMatrix.length; i++) {
            for (int j = i+1; j < transposedMatrix.length; j++) {
                boolean[] combinedSyndrome = new boolean[syndrome.length];
                for (int k = 0; k < syndrome.length; k++) {
                    combinedSyndrome[k] = transposedMatrix[i][k] ^ transposedMatrix[j][k];
                }

                if (Arrays.equals(combinedSyndrome, syndrome)) {
                    return new int[]{i+1, j+1}; // Zwracamy numery uszkodzonych bitów
                }
            }
        }

        return new int[]{-1}; // Jeśli nie znaleziono uszkodzonego bitu, zwracamy -1
    }

    boolean[] repairBit2Bit(boolean[] code, int[] brokenBits) {
        for (int brokenBit : brokenBits) {
            brokenBit--; // Zmniejszamy o 1, aby uzyskać indeks tablicy
            if (brokenBit >= 0 && brokenBit < code.length) {
                code[brokenBit] = !code[brokenBit]; // Naprawiamy uszkodzony bit
            }
        }
        return code;
    }
}
