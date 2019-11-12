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
import java.util.Arrays;

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
    public void encryptFile() {
        if (file != null) {
            Thread thread = new Thread(new EncryptFile());
            thread.start();
        }
    }

    @FXML
    public void decryptFile() {
        if(file != null) {
            Thread thread = new Thread(new DecryptFile());
            thread.start();
        }
    }

    @FXML
    public void encrypt() {
        Desx desx = new Desx();
        char[] text = ToTab.toCharTab(this.text.getText());

        int tmp = text.length;
        while(tmp%8 != 0)
            tmp++;

        char[] tmpText = new char[tmp];
        System.arraycopy(text, 0, tmpText, 0, text.length);
        Arrays.fill(tmpText, text.length, tmp, '\0');

        char[] key = rebuildKey(ToTab.toCharTab(this.key.getText()));
        char[] externalKey = rebuildKey(ToTab.toCharTab(this.externalKey.getText()));
        char[] internalKey = rebuildKey(ToTab.toCharTab(this.internalKey.getText()));

        cipherTextTab = desx.encrypt(tmpText, internalKey, key, externalKey);

        cipherText.setText(desx.getCipherTextString());
    }

    @FXML
    public void decrypt() {
        Desx desx = new Desx();
        char[] key = rebuildKey(ToTab.toCharTab(this.key.getText()));
        char[] externalKey = rebuildKey(ToTab.toCharTab(this.externalKey.getText()));
        char[] internalKey = rebuildKey(ToTab.toCharTab(this.internalKey.getText()));

        desx.decrypt(cipherTextTab, internalKey, key, externalKey);

        cipherText.setText(desx.getDecipherTextString());
    }

    @FXML
    public void copy() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cipherText.getText()), null);
    }

    private char[] rebuildKey(char[] key) {
        char[] tmp = new char[8];
        if(key.length > 8) {
            tmp = ToTab.cutTab(key, 0, 8);
        }
        else {
            System.arraycopy(key, 0, tmp, 0, key.length);
            Arrays.fill(tmp, key.length, 8, '\0');
        }
       return tmp;
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
                char[] key = rebuildKey(ToTab.toCharTab(keyFileTxt.getText()));
                char[] externalKey = rebuildKey(ToTab.toCharTab(externalKeyFile.getText()));
                char[] internalKey = rebuildKey(ToTab.toCharTab(internalKeyFile.getText()));


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
                char[] key = rebuildKey(ToTab.toCharTab(keyFileTxt.getText()));
                char[] externalKey = rebuildKey(ToTab.toCharTab(externalKeyFile.getText()));
                char[] internalKey = rebuildKey(ToTab.toCharTab(internalKeyFile.getText()));

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
