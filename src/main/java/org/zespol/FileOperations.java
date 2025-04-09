package org.zespol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperations {
    private int byteCount = 0;
    private static final HammingCode hamming = new HammingCode();

    public static final byte[] BIT_FLAGS = { (byte) 1, (byte) 2, (byte) 4, (byte) 8, (byte) 16, (byte) 32,
            (byte) 64, (byte) 128 };

    public byte[] readFromFile(String path) {
        File file = new File(path);
        byte[] byteArray = new byte[(int) file.length()];
        try (FileInputStream inputStream = new FileInputStream(file)) {
           byteCount = inputStream.read(byteArray);
           return byteArray;
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            return null;
        }
    }

    public boolean[][] encodeData(byte[] byteArray) {
        boolean[][] data = new boolean[byteCount][];
        boolean[] booleans = bytesToBooleanArray(byteArray);

        for (int i = 0; i < byteCount; i+=7) {
            boolean[] temp = new boolean[16];
            System.arraycopy(booleans, i, temp, 0, 8);
            data[i] = hamming.messageToCode2Bits(temp);
        }

        return data;
    }

    public void writeToFile(boolean[][] data, String path) {
        File file = new File(path);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            for (int i = 0; i < byteCount; i+=7) {
                byte[] byteArray = booleanArrayToBytes(data[i]);
                outputStream.write(byteArray);
            }
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }

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

    public byte[] booleanArrayToBytes(boolean[] input) {
        byte[] out = new byte[(int) Math.ceil(input.length / 8.0)];
        int pos = 0;
        for (boolean b : input) {
            if (b) {
                out[pos / 8] |= BIT_FLAGS[pos % 8];
            }
            pos++;
        }
        return out;
    }
}
