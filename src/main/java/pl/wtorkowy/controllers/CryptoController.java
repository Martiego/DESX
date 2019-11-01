package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypto.Des;
import pl.wtorkowy.crypto.Desx;

import java.io.*;

public class CryptoController {
    @FXML
    private Button encryptBtn;
    @FXML
    private Button decryptBtn;
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
    private Button chooseFileBtn;
    @FXML
    private Stage stage;

    private File fileEncrypt;
    private File fileDecrypt;

    private byte[] cipherText;
    private byte[] decipherText;

    @FXML
    public void openFileEncrypt() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otwórz Plik do zaszyfrowania");

        fileEncrypt = fileChooser.showOpenDialog(stage);

        if (fileEncrypt != null) {
            srcEncryptLbl.setText(fileEncrypt.getAbsolutePath());
        }
    }

    @FXML
    public void openFileDecrypt() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otwórz Plik do odszyfrowania");

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

            int[] tmp = new int[(int) fileEncrypt.length()];
            for(int i = 0; i < fileEncrypt.length(); i++) {
                tmp[i] = fileInputStream.read();
            }

            char[] text = ToTab.toCharTab(tmp);
            byte[] cipherFile;
            int[] cipherFileInt;

            cipherFile = desx.encrypt(text, internalKey, key, externalKey);
            cipherFileInt = ToTab.toIntTab(cipherFile);

            for (int value : cipherFileInt) {
                fileOutputStream.write(value);
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

            int[] tmp = new int[(int) fileDecrypt.length()];
            for(int i = 0; i < fileDecrypt.length(); i++) {
                tmp[i] = fileInputStream.read();
            }


            byte[] decipherFile;
            int[] decipherFileInt;

            decipherFile = desx.decrypt(ToTab.toByteTab(tmp), internalKey, key, externalKey);
            decipherFileInt = ToTab.toIntTab(decipherFile);

            for (int value : decipherFileInt) {
                fileOutputStream.write(value);
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
