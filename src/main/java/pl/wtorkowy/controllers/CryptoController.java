package pl.wtorkowy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypto.Des;
import pl.wtorkowy.crypto.Desx;

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
    private byte[] cipherText;
    private byte[] decipherText;

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
