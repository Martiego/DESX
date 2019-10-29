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

    public static int[] toIntegerTab(char[] block) {
        int[] blockInt = new int[8];
        for (int i = 0; i < 8; i++) { blockInt[i] = block[i]; }

        return blockInt;
    }

    public static byte[] toByteTab(int[] blockInt) {
        byte[] blockByte = new byte[64];
        int x = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 7 + x; j >= x; j--) {
                blockByte[j] = (byte) (blockInt[i]%2);
                blockInt[i] = blockInt[i]/2;
            }
            x += 8;
        }
        return blockByte;
    }

    public static byte[] toByteTab(int number) {
        byte[] blockByte = new byte[4];
        byte tmp;
        for (int i = 0; i < 4; i++) {
            blockByte[i] = (byte) (number%2);
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

    public static byte[] reverse(byte[] tab) {
        byte[] result = new byte[tab.length];

        for(int i = 0; i < tab.length / 2; i++) {
            byte temp = tab[i];
            result[i] = tab[tab.length -i -1];
            result[tab.length -i -1] = temp;
        }

        return result;
    }


}
