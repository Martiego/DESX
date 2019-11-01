package pl.wtorkowy.cast;

import java.util.Arrays;

public class ToTab {

    public static char[] toCharTab(String text) {
        char[] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i);
        }

        return result;
    }

    public static char[] toCharTab(int[] block) {
        char[] result = new char[block.length];
        for (int i = 0; i < block.length; i++) {
            result[i] = (char) block[i];
        }
        return result;
    }

    public static int[] toIntTab(byte[] block) {
        int[] result = new int[block.length/8];
        for (int i = 0; i < block.length/8; i++) {
            result[i] = toInt(cutTab(block, i * 8, 8));
        }

        return result;
    }

    public static int[] toIntegerTab(char[] block) {
        int[] blockInt = new int[8];
        for (int i = 0; i < 8; i++) { blockInt[i] = block[i]; }

        return blockInt;
    }

    public static byte[] toByteTab(int[] blockInt) {
        byte[] blockByte = new byte[blockInt.length*8];
        int x = 0;
        for (int i = 0; i < blockInt.length; i++) {
            for (int j = 7 + x; j >= x; j--) {
                blockByte[j] = (byte) (blockInt[i]%2);
                blockInt[i] = blockInt[i]/2;
            }
            x += 8;
        }
        return blockByte;
    }

    public static byte[] toByteTab(char[] block) {
        int[] tmp = toIntegerTab(block);

        return toByteTab(tmp);
    }

    public static byte[] toByteTab(byte number) {
        byte[] blockByte = new byte[4];
        byte tmp;

        for (int i = 0; i < 4; i++) {
            blockByte[i] = (byte) (number%2);
            number = (byte) (number/2);
        }

        for(int i = 0; i < 2; i++){
            tmp = blockByte[i];
            blockByte[i] = blockByte[3 - i];
            blockByte[3 - i] = tmp;
        }

        return blockByte;
    }

    public static int toInt(byte[] tab) {
        int result = 0;
        int temp = 1;

        for (int i = tab.length - 1; i >= 0; i--) {
            result += tab[i] * temp;
            temp *= 2;
        }
        return result;
    }

    public static byte[] cutTab(byte[] tab, int first, int count) {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = tab[first++];
        }
        return result;
    }

    public static char[] cutTab(char[] tab, int first, int count) {
        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = tab[first++];
        }
        return result;
    }

    public static void show(byte[] tab, String title, int blocks) {
        System.out.print("\n" + title);
        for (int i = 0; i < tab.length; i++) {
            if(i%blocks == 0)
                System.out.print("\n");

            System.out.print(tab[i]);
        }
    }
    public static void show(char[] tab, String title) {
        System.out.print("\n" + title);
        for (int i = 0; i < tab.length; i++) {
            if(i%8 == 0)
                System.out.print("\n");

            System.out.print(tab[i]);
        }
    }


}
