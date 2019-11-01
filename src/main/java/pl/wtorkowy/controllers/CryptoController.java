package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypto.Desx;

import java.io.*;

public class CryptoController {
    @FXML
    private TextArea textTxt;
    @FXML
    private TextField keyTxt;
    @FXML
    private TextField internalKeyTxt;
    @FXML
    private TextField externalKeyTxt;
    @FXML
    private Label cipherTextLbl;
    @FXML
    private Label srcEncryptLbl;
    @FXML
    private Label srcDecryptLbl;
    @FXML
    private Stage stage;

    private File fileEncrypt;
    private File fileDecrypt;

    private byte[] cipherText;
    private byte[] decipherText;

    @FXML
    public void openFileEncrypt() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otworz Plik do zaszyfrowania");

        fileEncrypt = fileChooser.showOpenDialog(stage);

        if (fileEncrypt != null) {
            srcEncryptLbl.setText(fileEncrypt.getAbsolutePath());
        }
    }

    @FXML
    public void openFileDecrypt() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otworz Plik do odszyfrowania");

        fileDecrypt = fileChooser.showOpenDialog(stage);

        if (fileDecrypt != null) {
            srcDecryptLbl.setText(fileDecrypt.getAbsolutePath());
        }
    }

    @FXML
    public void encryptFile() throws IOException {
        if (fileEncrypt.exists()) {
            FileInputStream fileInputStream = new FileInputStream(fileEncrypt);

            File newFile = new File(fileEncrypt.getAbsolutePath() + "ENCRYPT");
            newFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(newFile);

            Desx desx = new Desx();
            char[] key = ToTab.toCharTab(keyTxt.getText());
            char[] externalKey = ToTab.toCharTab(externalKeyTxt.getText());
            char[] internalKey = ToTab.toCharTab(internalKeyTxt.getText());

            int[] tmp = new int[8];
            byte[] cipherFile;
            int[] cipherFileInt;

            int times = (int) (fileEncrypt.length()/8);
            int rest = (int) (fileEncrypt.length() - times * 8);

            for(int i = 0; i < times; i++) {
                for (int j = 0; j < 8; j++) {
                    tmp[j] = fileInputStream.read();
                }
                cipherFile = desx.encrypt(ToTab.toCharTab(tmp), internalKey, key, externalKey);
                cipherFileInt = ToTab.toIntTab(cipherFile);
                for (int j = 0; j < 8; j++) {
                    fileOutputStream.write(cipherFileInt[j]);
                }
            }

            for (int i = 0; i < rest; i++) {
                tmp[i] = fileInputStream.read();
                System.out.println(tmp[i]);
            }

            cipherFile = desx.encrypt(ToTab.toCharTab(tmp), internalKey, key, externalKey);
            cipherFileInt = ToTab.toIntTab(cipherFile);

            for (int i: cipherFileInt) {
                fileOutputStream.write(i);
            }

            fileOutputStream.close();
            fileInputStream.close();

            srcEncryptLbl.setText("Zaszyfrowano");
        }
    }

    @FXML
    public void decryptFile() throws IOException {
        if(fileDecrypt.exists()) {
            FileInputStream fileInputStream = new FileInputStream(fileDecrypt);

            File newFile = new File(fileDecrypt.getAbsolutePath().replace("ENCRYPT", ""));
            newFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(newFile);

            Desx desx = new Desx();
            char[] key = ToTab.toCharTab(keyTxt.getText());
            char[] externalKey = ToTab.toCharTab(externalKeyTxt.getText());
            char[] internalKey = ToTab.toCharTab(internalKeyTxt.getText());

            int[] tmp = new int[8];
            byte[] decipherFile;
            int[] decipherFileInt;

            for(int i = 0; i < fileDecrypt.length()/8; i++) {
                for (int j = 0; j < 8; j++) {
                    tmp[j] = fileInputStream.read();
                }
                decipherFile = desx.decrypt(ToTab.toByteTab(tmp), internalKey, key, externalKey);
                decipherFileInt = ToTab.toIntTab(decipherFile);
                for (int j = 0; j < 8; j++) {
                    fileOutputStream.write(decipherFileInt[j]);
                }
            }

            fileOutputStream.close();
            fileInputStream.close();

            srcDecryptLbl.setText("Odszyfrowano");
        }
    }

    @FXML
    public void onClickEncrypt() {
        Desx desx = new Desx();
        char[] text = ToTab.toCharTab(textTxt.getText());
        char[] key = ToTab.toCharTab(keyTxt.getText());
        char[] externalKey = ToTab.toCharTab(externalKeyTxt.getText());
        char[] internalKey = ToTab.toCharTab(internalKeyTxt.getText());

        cipherText = desx.encrypt(text, internalKey, key, externalKey);

        cipherTextLbl.setText(desx.getCipherTextString());
    }

    @FXML
    public void onClickDecrypt() {
        Desx desx = new Desx();
        char[] key = ToTab.toCharTab(keyTxt.getText());
        char[] externalKey = ToTab.toCharTab(externalKeyTxt.getText());
        char[] internalKey = ToTab.toCharTab(internalKeyTxt.getText());

        decipherText = desx.decrypt(cipherText, internalKey, key, externalKey);

        cipherTextLbl.setText(desx.getDecipherTextString());
    }
}
