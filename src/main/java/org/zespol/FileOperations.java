package org.zespol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za operacje na plikach, kodowanie i dekodowanie danych
 * przy użyciu kodu Hamminga.
 */
public class FileOperations {
    /**
     * Tablica bajtów reprezentująca flagi bitowe używane do operacji bitowych
     * Każda pozycja odpowiada pojedynczemu bitowi (1, 2, 4, 8, 16, 32, 64, 128)
     */
    public static final byte[] BIT_FLAGS = {(byte) 1, (byte) 2, (byte) 4, (byte) 8, (byte) 16, (byte) 32,
            (byte) 64, (byte) 128};
    /**
     * Instancja klasy HammingCode używana do kodowania i dekodowania danych
     */
    private static final HammingCode hamming = new HammingCode();

    /**
     * Odczytuje dane z pliku tekstowego.
     *
     * @param path Ścieżka do pliku (String)
     * @return Tablica bajtów zawierająca dane z pliku lub null w przypadku błędu
     */
    public byte[] readTextFromFile(String path) {
        File file = new File(path);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
            inputStream.read(byteArray);
            return byteArray;
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            return null;
        }
    }

    /**
     * Koduje dane przy użyciu kodu Hamminga wykrywającego 2 bity błędów.
     *
     * @param byteArray Tablica bajtów do zakodowania
     * @return Dwuwymiarowa tablica boolean zawierająca zakodowane dane
     */
    public boolean[][] encodeData(byte[] byteArray) {
        int numBytes = byteArray.length;
        boolean[][] data = new boolean[numBytes][16];
        boolean[] boolArray = bytesToBooleanArray(byteArray);

        for (int i = 0; i < numBytes; i++) {
            boolean[] temp = new boolean[8];
            System.arraycopy(boolArray, i * 8, temp, 0, 8);
            data[i] = hamming.messageToCode2Bits(temp);
        }

        return data;
    }

    /**
     * Zapisuje zakodowane dane do pliku.
     *
     * @param data Dwuwymiarowa tablica boolean z zakodowanymi danymi
     * @param path Ścieżka do pliku docelowego (String)
     */
    public void writeEncodedToFile(boolean[][] data, String path) {
        File file = new File(path);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            for (boolean[] row : data) {
                for (boolean bit : row) {
                    // Zapisz '1' jeśli bit jest true, w przeciwnym razie zapisz '0'
                    outputStream.write(bit ? '1' : '0');
                }
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }

    /**
     * Odczytuje zakodowane dane z pliku i naprawia ewentualne błędy.
     *
     * @param path Ścieżka do pliku (String)
     * @return Dwuwymiarowa tablica boolean z odkodowanymi i naprawionymi danymi lub null w przypadku błędu
     */
    public boolean[][] readEncodedFromFile(String path) {
        File file = new File(path);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            // Odczytujemy zawartość pliku jako znaki
            byte[] byteArray = new byte[(int) file.length()];
            inputStream.read(byteArray);

            // Konwertujemy znaki ASCII '0' i '1' na wartości logiczne
            int numCodeWords = byteArray.length / 16;
            boolean[][] data = new boolean[numCodeWords][16];

            for (int i = 0; i < numCodeWords; i++) {
                boolean[] codeWord = new boolean[16];

                for (int j = 0; j < 16; j++) {
                    // '1' ma kod ASCII 49
                    codeWord[j] = (byteArray[i * 16 + j] == 49);
                }

                boolean[] syndrome = hamming.calculateSyndrome2Bits(codeWord);
                data[i] = hamming.repairBit2Bit(codeWord, hamming.whichBitBroken2Bit(syndrome));
            }
            return data;
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            return null;
        }
    }

    /**
     * Dekoduje dane zakodowane kodem Hamminga.
     *
     * @param data Dwuwymiarowa tablica boolean z zakodowanymi danymi
     * @return Odkodowany tekst jako String w kodowaniu UTF-8
     */
    public String decodeData(boolean[][] data) {
        byte[] bytes = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            boolean[] decodedBits = hamming.codeToMessage2Bits(data[i]);
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                if (decodedBits[j]) {
                    b |= (byte) (1 << j);
                }
            }
            bytes[i] = b;
        }
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Zapisuje tekst do pliku.
     *
     * @param text Tekst do zapisania (String)
     * @param path Ścieżka do pliku docelowego (String)
     */
    public void writeTextTofile(String text, String path) {
        File file = new File(path);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(text.getBytes());
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }

    /**
     * Konwertuje tablicę bajtów na tablicę wartości boolean.
     * Każdy bajt jest reprezentowany przez 8 wartości boolean.
     *
     * @param input Tablica bajtów wejściowych
     * @return Tablica boolean reprezentująca bity wejściowych bajtów
     */
    public boolean[] bytesToBooleanArray(byte[] input) {
        boolean[] out = new boolean[input.length * 8];

        int pos = 0;
        for (byte b : input) {
            for (int j = 0; j < 8; j++, pos++) {
                out[pos] = (b & BIT_FLAGS[j]) == BIT_FLAGS[j];
            }
        }

        return out;
    }
}