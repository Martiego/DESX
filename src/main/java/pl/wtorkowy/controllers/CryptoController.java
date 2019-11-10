package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ProgressBar progressBar;
    private double progress = 0;
    private double tmpProgress;

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
        if (file != null) {
            EncryptFile encryptFile = new EncryptFile();
            Thread thread = new Thread(encryptFile);
            thread.start();
        }
    }

    @FXML
    public void decryptFile() throws IOException {
        if(file != null) {
            DecryptFile decryptFile = new DecryptFile();
            Thread thread = new Thread(decryptFile);
            thread.start();
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

    public class EncryptFile implements Runnable {

        @Override
        public void run() {
            try {
                progressBar.setProgress(progress);
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
                tmpProgress = 1.0/times;

                for(int i = 0; i < times; i++) {
                    progressBar.setProgress(progress+=tmpProgress);
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
                }

                for (int i = rest; i < 8; i++) {
                    tmp[i] = '\0';
                }

                cipherFile = desx.encrypt(ToTab.toCharTab(tmp), internalKey, key, externalKey);
                cipherFileInt = ToTab.toIntTab(cipherFile);

                for (int i: cipherFileInt) {
                    fileOutputStream.write(i);
                }

                fileOutputStream.close();
                fileInputStream.close();

                progress = 0;
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public class DecryptFile implements Runnable {

        @Override
        public void run() {
            try {
                progressBar.setProgress(progress);
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
                tmpProgress = 1.0/(file.length()/8.0);

                for(int i = 0; i < file.length()/8; i++) {
                    progressBar.setProgress(progress+=tmpProgress);
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

                progress = 0;
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
