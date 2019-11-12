package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.cast.Xor;

public class Desx {
    private byte[] cipherText;
    private byte[] decipherText;
    private Des des;

    public Desx() {
        des = new Des();
    }

    public byte[] encrypt(char[] plainText, char[] keyInternal, char[] keyDes, char[] keyExternal) {
        byte[] tmpCipher;

        cipherText = new byte[plainText.length*8];

        for(int i = 0; i < plainText.length/8; i++){
            tmpCipher = Xor.xorByteTab(ToTab.toByteTab(keyExternal), des.encrypt(Xor.xorCharTab(ToTab.cutTab(plainText, i * 8, 8), keyInternal), keyDes));
            System.arraycopy(tmpCipher, 0, cipherText, i * 64, 64);
        }

        return cipherText;
    }

    public byte[] decrypt(byte[] plainText, char[] keyInternal, char[] keyDes, char[] keyExternal) {
        byte[] tmpCipher;
        decipherText = new byte[plainText.length];

        for(int i = 0; i < plainText.length/64; i++){
            tmpCipher = Xor.xorByteTab(ToTab.toByteTab(keyInternal), des.decrypt(Xor.xorByteTab(ToTab.cutTab(plainText, i * 64, 64), ToTab.toByteTab(keyExternal)), keyDes));
            System.arraycopy(tmpCipher, 0, decipherText, i * 64, 64);
        }

        return decipherText;
    }


    public String getCipherTextString() {
        char[] tab = new char[cipherText.length/8];
        for (int i = 0; i < cipherText.length/8; i++) {
            tab[i] = (char) (ToTab.toInt(ToTab.cutTab(cipherText, i*8, 8)));
        }

        return new String(tab);
    }

    public String getDecipherTextString() {
        char[] tab = new char[decipherText.length/8];
        for (int i = 0; i < decipherText.length/8; i++) {
            tab[i] = (char) (ToTab.toInt(ToTab.cutTab(decipherText, i*8, 8)));
        }

        return new String(tab);
    }
}
