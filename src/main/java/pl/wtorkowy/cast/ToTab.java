package pl.wtorkowy.cast;

public class ToTab {

    public static char [] toCharTab(String text) {
        char [] result = new char [text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i);
        }

        return result;
    }

    public static int [] toIntegerTab(char [] block) {
        int [] blockInt = new int[8];
        for (int i = 0; i < 8; i++) { blockInt[i] = block[i]; }

        return blockInt;
    }

    public static byte [] toByteTab(int [] blockInt) {
        byte [] blockByte = new byte[64];
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
}
