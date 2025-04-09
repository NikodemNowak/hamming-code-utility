package org.zespol;

import java.util.Arrays;

/**
 * Klasa implementująca algorytmy kodowania i dekodowania z wykorzystaniem kodu Hamminga.
 * Zawiera implementacje dla kodów wykrywających 1 bit błędu oraz 2 bity błędów.
 */
public class HammingCode {

    /**
     * Macierz kontrolna dla kodu Hamminga wykrywającego 1 bit błędu.
     * Wiersze reprezentują bity parzystości, a kolumny pozycje w słowie kodowym.
     */
    boolean[][] parityBitsControlMatrix1Bit = {{
            true, false, true, false, true, false, true, false, true, false, true, false
    }, {
            false, true, true, false, false, true, true, false, false, true, true, false
    }, {
            false, false, false, true, true, true, true, false, false, false, false, true
    }, {
            false, false, false, false, false, false, false, true, true, true, true, true
    }};
    /**
     * Macierz kontrolna dla kodu Hamminga wykrywającego 2 bity błędu.
     * Wiersze reprezentują bity parzystości, a kolumny pozycje w słowie kodowym.
     */
    boolean[][] parityBitsControlMatrix2Bits = {
            {true, false, true, false, true, false, true, false, true, false, false, false, false, false, false, false},
            {false, true, true, false, false, true, true, false, false, true, false, false, false, false, false, false},
            {false, false, false, true, true, true, true, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, true, false, false, false, true, false, false, false, false},
            {true, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false},
            {false, false, true, true, false, false, false, false, false, false, false, false, false, true, false, false},
            {false, false, false, false, true, true, false, false, false, false, false, false, false, false, true, false},
            {false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, true}
    };

    /**
     * Transponuje macierz boolowską.
     *
     * @param matrix Macierz wejściowa (tablica dwuwymiarowa boolean)
     * @return Przetransponowana macierz
     */
    public static boolean[][] transposeMatrix(boolean[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] transposedMatrix = new boolean[n][m];

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                transposedMatrix[x][y] = matrix[y][x];
            }
        }

        return transposedMatrix;
    }

    /**
     * Koduje wiadomość 8-bitową za pomocą kodu Hamminga wykrywającego 1 bit błędu.
     *
     * @param message Tablica boolean reprezentująca oryginalną wiadomość
     * @return Tablica boolean zawierająca 12-bitowe słowo kodowe
     */
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

    /**
     * Oblicza syndrom dla zakodowanej wiadomości, który służy do wykrywania błędów.
     *
     * @param code Tablica boolean zawierająca słowo kodowe
     * @return Tablica boolean reprezentująca syndrom
     */
    public boolean[] calculateSyndrome1Bit(boolean[] code) {
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix1Bit);
        boolean[] syndrome = new boolean[4];
        boolean[][] temp = new boolean[code.length][transposedMatrix[0].length];

        for (int i = 0; i < code.length; i++) {
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

    /**
     * Określa, który bit jest uszkodzony w kodzie na podstawie syndromu.
     *
     * @param resultCode Tablica boolean reprezentująca syndrom
     * @return Numer pozycji uszkodzonego bitu (1-indeksowany) lub -1, jeśli nie znaleziono
     */
    public int whichBitBroken1Bit(boolean[] resultCode) {
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix1Bit);
        for (int i = 0; i < transposedMatrix.length; i++) {
            boolean[] row = transposedMatrix[i];
            boolean isBroekn = true; // Zakładamy, że bit kodu jest uszkodzony
            for (int j = 0; j < row.length; j++) {
                if (row[j] != resultCode[j]) {
                    isBroekn = false;   // Jeśli chociaż 1 bit wiersza różni się od syndromu tzn, że nie ten bit kodu jest uszkodzony
                    break;
                }
            }
            if (isBroekn) {
                return (i + 1); // Zwracamy numer uszkodzonego bitu
            }
        }
        return -1; // Jeśli nie znaleziono uszkodzonego bitu, zwracamy -1
    }

    /**
     * Naprawia uszkodzony bit w słowie kodowym.
     *
     * @param code      Tablica boolean reprezentująca słowo kodowe
     * @param brokenBit Numer pozycji uszkodzonego bitu (1-indeksowany)
     * @return Tablica boolean zawierająca naprawione słowo kodowe
     */
    public boolean[] repairBit1Bit(boolean[] code, int brokenBit) {

        if (brokenBit == -1) {
            return code; // Jeśli nie znaleziono uszkodzonego bitu, zwracamy oryginalny kod
        }

        brokenBit--; // Zmniejszamy o 1, aby uzyskać indeks tablicy
        if (brokenBit >= 0 && brokenBit < code.length) {
            code[brokenBit] = !code[brokenBit]; // Naprawiamy uszkodzony bit
        }
        return code;
    }

    /**
     * Koduje wiadomość 8-bitową za pomocą kodu Hamminga wykrywającego 2 bity błędu.
     *
     * @param message Tablica boolean reprezentująca oryginalną wiadomość
     * @return Tablica boolean zawierająca 16-bitowe słowo kodowe
     */
    public boolean[] messageToCode2Bits(boolean[] message) {
        boolean[] code = new boolean[16];

        System.arraycopy(message, 0, code, 0, message.length);

        code[8] = code[0] ^ code[2] ^ code[4] ^ code[6];
        code[9] = code[1] ^ code[2] ^ code[5] ^ code[6];
        code[10] = code[3] ^ code[4] ^ code[5] ^ code[6];
        code[11] = code[7];
        code[12] = code[0] ^ code[1];
        code[13] = code[2] ^ code[3];
        code[14] = code[4] ^ code[5];
        code[15] = code[6] ^ code[7];

        return code;
    }

    /**
     * Oblicza syndrom dla kodu Hamminga wykrywającego 2 bity błędu.
     *
     * @param code Tablica boolean zawierająca słowo kodowe
     * @return Tablica boolean reprezentująca syndrom
     */
    public boolean[] calculateSyndrome2Bits(boolean[] code) {
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix2Bits);
        boolean[] syndrome = new boolean[8];
        boolean[][] temp = new boolean[code.length][transposedMatrix[0].length];

        for (int i = 0; i < code.length; i++) {
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

    /**
     * Określa, które bity są uszkodzone w kodzie na podstawie syndromu.
     * Może wykryć 1 lub 2 uszkodzone bity.
     *
     * @param syndrome Tablica boolean reprezentująca syndrom
     * @return Tablica int zawierająca numery uszkodzonych bitów (1-indeksowane) lub [-1] jeśli nie znaleziono
     */
    public int[] whichBitBroken2Bit(boolean[] syndrome) {
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix2Bits);
        for (int i = 0; i < transposedMatrix.length; i++) {
            boolean[] row = transposedMatrix[i];
            boolean isBroken = true;
            for (int j = 0; j < row.length; j++) {
                if (row[j] != syndrome[j]) {
                    isBroken = false;
                    break;
                }
            }
            if (isBroken) {
                return new int[]{i + 1}; // Zwracamy numer uszkodzonego bitu
            }
        }

        // Sprawdzamy kombinacje 2 bitów
        // Dla dwóch uszkodzonych bitów, syndrom będzie XORem odpowiednich wierszy macierzy
        for (int i = 0; i < transposedMatrix.length; i++) {
            for (int j = i + 1; j < transposedMatrix.length; j++) {
                boolean[] combinedSyndrome = new boolean[syndrome.length];
                for (int k = 0; k < syndrome.length; k++) {
                    combinedSyndrome[k] = transposedMatrix[i][k] ^ transposedMatrix[j][k];
                }

                if (Arrays.equals(combinedSyndrome, syndrome)) {
                    return new int[]{i + 1, j + 1}; // Zwracamy numery uszkodzonych bitów
                }
            }
        }

        return new int[]{-1}; // Jeśli nie znaleziono uszkodzonego bitu, zwracamy -1
    }

    /**
     * Naprawia uszkodzone bity w słowie kodowym.
     *
     * @param code       Tablica boolean reprezentująca słowo kodowe
     * @param brokenBits Tablica int zawierająca numery uszkodzonych bitów (1-indeksowane)
     * @return Tablica boolean zawierająca naprawione słowo kodowe
     */
    public boolean[] repairBit2Bit(boolean[] code, int[] brokenBits) {

        for (int bit : brokenBits) {
            if (bit == -1) {
                return code; // Jeśli którykolwiek bit jest -1, zwracamy oryginalny kod
            }
        }

        for (int brokenBit : brokenBits) {
            brokenBit--; // Zmniejszamy o 1, aby uzyskać indeks tablicy
            if (brokenBit >= 0 && brokenBit < code.length) {
                code[brokenBit] = !code[brokenBit]; // Naprawiamy uszkodzony bit
            }
        }
        return code;
    }

    /**
     * Dekoduje słowo kodowe Hamminga do oryginalnej wiadomości.
     *
     * @param code Tablica boolean zawierająca 16-bitowe słowo kodowe
     * @return Tablica boolean zawierająca 8-bitową odkodowaną wiadomość
     */
    public boolean[] codeToMessage2Bits(boolean[] code) {
        boolean[] message = new boolean[8];
        System.arraycopy(code, 0, message, 0, message.length);
        return message;
    }
}