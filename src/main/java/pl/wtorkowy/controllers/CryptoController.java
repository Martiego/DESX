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

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;

public class CryptoController {
    @FXML
    private TextField keyFileTxt;
    @FXML
    private TextField internalKey;
    @FXML
    private TextField key;
    @FXML
    private TextField externalKey;
    @FXML
    private TextField internalKeyFile;
    @FXML
    private TextField externalKeyFile;

    @FXML
    private TextArea text;
    @FXML
    private Label cipherText;
    @FXML
    private TextField nameFile;
    @FXML
    private Label path;

    @FXML
    private Button chooseFile;
    @FXML
    private Button encrypt;
    @FXML
    private Button encryptFile;
    @FXML
    private Button decrypt;
    @FXML
    private Button decryptFile;
    @FXML
    private Stage stage;
    @FXML
    private File file;
    @FXML
    private byte[] cipherTextTab;

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otworz Plik do zaszyfrowania");

        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            path.setText(file.getAbsolutePath());
        }


    }

    @FXML
    public void encryptFile() throws IOException {
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);

            String name = ToTab.replace(file.getAbsolutePath(), File.separatorChar, nameFile.getText());
            File newFile = new File(name);
            newFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(newFile);

            Desx desx = new Desx();
            char[] key = ToTab.toCharTab(keyFileTxt.getText());
            char[] externalKey = ToTab.toCharTab(externalKeyFile.getText());
            char[] internalKey = ToTab.toCharTab(internalKeyFile.getText());

            int[] tmp = new int[8];
            byte[] cipherFile;
            int[] cipherFileInt;

            int times = (int) (file.length()/8);
            int rest = (int) (file.length() - times * 8);

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

            path.setText("Zaszyfrowano");
        }
    }

    @FXML
    public void decryptFile() throws IOException {
        if(file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);

            String name = ToTab.replace(file.getAbsolutePath(), File.separatorChar, nameFile.getText());
            File newFile = new File(name);
            newFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(newFile);

            Desx desx = new Desx();
            char[] key = ToTab.toCharTab(keyFileTxt.getText());
            char[] externalKey = ToTab.toCharTab(externalKeyFile.getText());
            char[] internalKey = ToTab.toCharTab(internalKeyFile.getText());

            int[] tmp = new int[8];
            byte[] decipherFile;
            int[] decipherFileInt;

            for(int i = 0; i < file.length()/8; i++) {
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

            path.setText("Odszyfrowano");
        }
    }

    @FXML
    public void encrypt() {
        Desx desx = new Desx();
        char[] text = ToTab.toCharTab(this.text.getText());
        char[] key = ToTab.toCharTab(this.key.getText());
        char[] externalKey = ToTab.toCharTab(this.externalKey.getText());
        char[] internalKey = ToTab.toCharTab(this.internalKey.getText());

        cipherTextTab = desx.encrypt(text, internalKey, key, externalKey);

        cipherText.setText(desx.getCipherTextString());
    }

    @FXML
    public void decrypt() {
        Desx desx = new Desx();
        char[] key = ToTab.toCharTab(this.key.getText());
        char[] externalKey = ToTab.toCharTab(this.externalKey.getText());
        char[] internalKey = ToTab.toCharTab(this.internalKey.getText());

        desx.decrypt(cipherTextTab, internalKey, key, externalKey);

        cipherText.setText(desx.getDecipherTextString());
    }

    @FXML
    public void copy() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cipherText.getText()), null);
    }
}
