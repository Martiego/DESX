package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

public class KeyBlock {
    private char[] block;
    private int[] blockInt;

    public byte[] getBlockByte() {
        return blockByte;
    }

    private byte[] blockByte;
    private byte[] leftBlock = new byte[28];
    private byte[] rightBlock = new byte[28];
    private byte[] connectedBlock = new byte[56];
    private byte[] permutedChoiceTwo = new byte[48];
    private byte[] leftPattern = {
            57, 49, 41, 33, 25, 17,  9,
             1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36
    };
    private byte[] rightPattern = {
            63, 55, 47, 39, 31, 23, 15,
             7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4
    };
    private byte[] patternPermutedChoiceTwo = {
            14, 17, 11, 24,  1,  5,  3,
            28, 15,  6, 21, 10, 23, 19,
            12,  4, 26,  8, 16,  7, 27,
            20, 13,  2, 41, 52, 31, 37,
            47, 55, 30, 40, 51, 45, 33,
            48, 44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };
    private byte[] shiftTable = {
            1, 1, 2, 2, 2, 2, 2, 2,
            1, 2, 2, 2, 2, 2, 2, 1, 0
    };

    public KeyBlock(char[] block) {
        this.block = block;
        blockInt = ToTab.toIntegerTab(block);
        blockByte = ToTab.toByteTab(blockInt);
        leftBlock = Permutation.permutation(leftPattern, blockByte, 28);
        rightBlock = Permutation.permutation(rightPattern, blockByte, 28);
    }

    public void roundEncrypt(int round) {
        leftShift(shiftTable[round]);
        connectBlock();
        permutationChoiceTwo();
    }

    public void roundDecrypt(int round) {
        rightShift(shiftTable[round]);
        connectBlock();
        permutationChoiceTwo();
    }

    public void leftShift(byte times) {
        byte tmpL = 0;
        byte tmpR = 0;

        for (int j = 0; j < times; j++) {
            tmpL = leftBlock[0];
            tmpR = rightBlock[0];

            for (int i = 0; i < 27; i++) {
                leftBlock[i] = leftBlock[i + 1];
                rightBlock[i] = rightBlock[i + 1];
            }
            leftBlock[27] = tmpL;
            rightBlock[27] = tmpR;
        }
    }

    public void rightShift(byte times) {
        byte tmpL = 0;
        byte tmpR = 0;

        for (int j = 0; j < times; j++) {
            tmpL = leftBlock[27];
            tmpR = rightBlock[27];
            for (int i = 27; i > 0; i--) {
                leftBlock[i] = leftBlock[i - 1];
                rightBlock[i] = rightBlock[i - 1];
            }
            leftBlock[0] = tmpL;
            rightBlock[0] = tmpR;
        }
    }

    public void connectBlock() {
        System.arraycopy(leftBlock, 0, connectedBlock, 0, 28);
        System.arraycopy(rightBlock, 0, connectedBlock, 28, 28);
    }

    public void permutationChoiceTwo() {
        permutedChoiceTwo = Permutation.permutation(patternPermutedChoiceTwo, connectedBlock, 48);
    }

    public byte[] getPermutedChoiceTwo() {
        return permutedChoiceTwo;
    }
}
