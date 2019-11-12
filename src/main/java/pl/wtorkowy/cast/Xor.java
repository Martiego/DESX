package pl.wtorkowy.cast;

public class Xor {

    public static char[] xorCharTab(char[] first, char[] second) {
        char[] result = new char[second.length];

        for (int i = 0; i < second.length; i++) {
            result[i] = (char) (first[i] ^ second[i]);
        }

        return result;
    }

    public static byte[] xorByteTab(byte[] first, byte[] second) {
        byte[] result = new byte[second.length];

        for (int i = 0; i < second.length; i++) {
            result[i] = (byte) (first[i] ^ second[i]);
        }

        return result;
    }

}
