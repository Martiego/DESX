package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

import java.util.Arrays;

public class DataBlock {
    private char[] block = new char[] { '1', '1', '7', '7', '7', '7', '7', '?'};
    private int[] blockInt;
    private byte[] blockByte;
    private byte[] blockInitialPermutation = new byte[64];
    private byte[] blockLeft = new byte[32];
    private byte[] blockRight = new byte[32];
    private byte[] blockRightExtended = new byte[32];
    private byte[] initialPermutationPattern = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,  9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    private byte[] expansionPermutationPattern = {
            32,  1,  2,  3,  4,  5,
             4,  5,  6,  7,  8,  9,
             8,  9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32,  1
    };


    public DataBlock() {
          blockInt = ToTab.toIntegerTab(block);
          blockByte = ToTab.toByteTab(blockInt);
          blockInitialPermutation = Permutation.permutation(initialPermutationPattern, blockByte, 64);
          divideBlock();
          blockRightExtended = Permutation.permutation(expansionPermutationPattern, blockInitialPermutation, 48);

    }

    public void divideBlock() {
        System.arraycopy(blockInitialPermutation, 0, blockLeft, 0, 32);
        System.arraycopy(blockInitialPermutation, 32, blockRight, 0, 32);
    }

    public void expansionPermutation() {

    }
}
