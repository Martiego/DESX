package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

public class KeyBlock {
    private char [] block = new char[] { '1', '1', '7', '7', '7', '7', '7', '?'};
    private int [] blockInt;
    private byte [] blockByte;
    private byte [] leftPattern = {
            57, 49, 41, 33, 25, 17,  9,
             1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36
    };
    private byte [] rightPattern = {
            63, 55, 47, 39, 31, 23, 15,
             7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4
    };
    private byte [] leftBlock = new byte[28];
    private byte [] rightBlock = new byte[28];

    public KeyBlock() {
        blockInt = ToTab.toIntegerTab(block);
        blockByte = ToTab.toByteTab(blockInt);
    }

    private void keyPermutation() {
        for (int i = 0; i < 28; i++) {
            leftBlock[i] = blockByte[leftPattern[i] - 1];
            rightBlock[i] = blockByte[rightPattern[i] - 1];
        }
    }


}
