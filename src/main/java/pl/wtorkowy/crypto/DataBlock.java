package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

public class DataBlock {
    private char[] block;
    private int[] blockInt;
    private byte[] blockByte;
    private byte[] blockInitialPermutation = new byte[64];
    private byte[] blockLeft = new byte[32];
    private byte[] blockRight = new byte[32];
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

    public DataBlock(char[] block) {
        this.block = block;
        blockInt = ToTab.toIntegerTab(block);
        blockByte = ToTab.toByteTab(blockInt);
        blockInitialPermutation = Permutation.permutation(initialPermutationPattern, blockByte, 64);
        divideBlock();
    }

    public DataBlock(byte[] block) {
        blockByte = block;
        blockInitialPermutation = Permutation.permutation(initialPermutationPattern, blockByte, 64);
        divideBlock();
    }

    public void divideBlock() {
        System.arraycopy(blockInitialPermutation, 0, blockLeft, 0, 32);
        System.arraycopy(blockInitialPermutation, 32, blockRight, 0, 32);
    }

    public byte[] getBlockLeft() {
        return blockLeft;
    }

    public byte[] getBlockRight() {
        return blockRight;
    }

    public void setBlockRight(byte[] blockRight) {
        this.blockRight = blockRight;
    }
}
