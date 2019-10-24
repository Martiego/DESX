package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

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


}
