package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

import java.util.Arrays;

public class DataBlock {
    private char [] block = new char[] { '1', '1', '7', '7', '7', '7', '7', '?'};
    private int [] blockInt;
    private byte [] blockByte;
    private byte [] blockInitialPermutation = new byte[64];
    private byte [] pattern = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,  9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    private byte [] leftBlock = new byte[32];
    private byte [] rightBlock = new byte[32];

    public DataBlock() {
          blockInt = ToTab.toIntegerTab(block);
          blockByte = ToTab.toByteTab(blockInt);
          makeInitialPermutation();
          divideBlocks();
    }

    private void makeInitialPermutation() {
        for (int i = 0; i < 64; i++) {
            blockInitialPermutation[i] = blockByte[pattern[i]-1];
        }
    }

    private void divideBlocks() {
        for (int i = 0; i < 32; i++) {
            leftBlock[i] = blockInitialPermutation[i];
            rightBlock[i] = blockInitialPermutation[32 + i];
        }
    }

    @Override
    public String toString() {
        return "DataBlock{" +
                "blockByte=" + Arrays.toString(blockByte) +
                ", \nblockInitialPermutation=" + Arrays.toString(blockInitialPermutation) +
                '}';
    }
}
