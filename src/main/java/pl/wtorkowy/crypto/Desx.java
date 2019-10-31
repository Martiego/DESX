package pl.wtorkowy.crypto;

import pl.wtorkowy.cast.ToTab;

import java.util.Arrays;

public class Desx {
    private char[] keyInternal = new char[8];
    private char[] keyDes = new char[8];
    private char[] keyExternal = new char[8];
    private byte[] cipherText;
    private byte[] decipherText;
    private Des des;

    public Desx() {
        des = new Des();
    }

    public byte[] encrypt(char[] plainText, char[] keyInternal, char[] keyDes, char[] keyExternal) {
        rebuildKeys(keyInternal, keyDes, keyExternal);

        int tmp = plainText.length;
        byte[] tmpCipher;
        char[] tmpText;

        while(tmp%8 != 0) {
            tmp++;
        }

        tmpText = new char[tmp];
        System.arraycopy(plainText, 0, tmpText, 0, plainText.length);
        Arrays.fill(tmpText,  plainText.length, tmp,  '\0');

        cipherText = new byte[tmp*8];

        for(int i = 0; i < tmp/8; i++){
            tmpCipher = Xor.xorByteTab(ToTab.toByteTab(this.keyExternal), des.encrypt(Xor.xorCharTab(ToTab.cutTab(tmpText, i * 8, 8), this.keyInternal), this.keyDes));
            System.arraycopy(tmpCipher, 0, cipherText, i * 64, 64);
        }

        return cipherText;
    }

    public byte[] decrypt(byte[] plainText, char[] keyInternal, char[] keyDes, char[] keyExternal) {
        rebuildKeys(keyInternal, keyDes, keyExternal);

        int tmp = plainText.length;
        byte[] tmpCipher;
        decipherText = new byte[tmp];

        for(int i = 0; i < tmp/64; i++){
            tmpCipher = Xor.xorByteTab(ToTab.toByteTab(this.keyInternal), des.decrypt(Xor.xorByteTab(ToTab.cutTab(plainText, i * 64, 64), ToTab.toByteTab(this.keyExternal)), this.keyDes));
            System.arraycopy(tmpCipher, 0, decipherText, i * 64, 64);
        }

        return decipherText;
    }

    private void rebuildKeys(char[] keyInternal, char[] keyDes, char[] keyExternal) {
        //Zabezpieczenie, gdyby ktoś podał klucz za krótki
        if(keyExternal.length > 8) {
            System.arraycopy(keyInternal, 0, this.keyInternal, 0, 8);
        }
        else {
            System.arraycopy(keyInternal, 0, this.keyInternal, 0 , keyInternal.length);
            Arrays.fill(this.keyInternal,  keyInternal.length, 8,  '\0');
        }
        if(keyDes.length > 8) {
            System.arraycopy(keyDes, 0, this.keyDes, 0, 8);
        }
        else {
            System.arraycopy(keyDes, 0, this.keyDes, 0 , keyDes.length);
            Arrays.fill(this.keyDes, keyDes.length, 8, '\0');
        }
        if(keyExternal.length > 8) {
            System.arraycopy(keyExternal, 0, this.keyExternal, 0, 8);
        }
        else {
            System.arraycopy(keyExternal, 0, this.keyExternal, 0 , keyExternal.length);
            Arrays.fill(this.keyExternal, keyExternal.length, 8, '\0');
        }
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
