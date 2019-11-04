package pl.wtorkowy.cast;

public class Xor {

    public static String xorCharTab(String text, String key, int lenght) {
        char [] result = new char[lenght];
        char [] textDesx = ToTab.toCharTab(text);
        char [] keyDesx = ToTab.toCharTab(key);

        for (int i = 0; i < lenght; i++) {
            result[i] = (char)(textDesx[i] ^ keyDesx[i]);
        }

        return new String(result);
    }

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
