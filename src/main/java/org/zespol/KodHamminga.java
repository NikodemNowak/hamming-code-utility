package org.zespol;

import java.util.Arrays;

public class KodHamminga {
    boolean[] message = new boolean[8];
    boolean[][] parityBitsControlMatrix1 = {{
        true, false, true, false, true, false, true, false, true, false, true, false
    }, {
        false, true, true, false, false, true, true, false, false, true, true, false
    }, {
        false, false, false, true, true, true, true, false, false, false, false, true
    }, {
        false, false, false, false ,false, false, false, true, true, true, true, true
    }};

    public boolean[] messageToCode1(boolean[] message) {
        boolean[] code = new boolean[12];
        for (int i = 0; i < message.length; i++) {
            if (i == 0 || i == 2 || i == 4 || i == 8) {
                continue;
            }
            code[i] = message[i];
        }

        code[0] = code[2] ^ code[4] ^ code[6] ^ code[8] ^ code[10];
        code[1] = code[2] ^ code[5] ^ code[6] ^ code[9] ^ code[10];
        code[3] = code[4] ^ code[5] ^ code[6] ^ code[11];
        code[7] = code[8] ^ code[9] ^ code[10] ^ code[11];

        return code;
    }

    public boolean[] verifyCode(boolean[] code) {
        boolean[][] transposedMatrix = transposeMatrix(parityBitsControlMatrix1);
        boolean[] resultCode = new boolean[4];
        boolean[][] temp = new boolean[code.length][transposedMatrix[0].length];

        for (int i=0; i<code.length; i++) {
            if (code[i]) {
                System.arraycopy(transposedMatrix[i], 0, temp[i], 0, transposedMatrix[i].length);
            }
        }

        for (boolean[] booleans : temp) {
            for (int j = 0; j < booleans.length; j++) {
                resultCode[j] ^= booleans[j];
            }
        }

        System.out.println("resultCode: " + Arrays.toString(resultCode));

        return resultCode;
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
}
