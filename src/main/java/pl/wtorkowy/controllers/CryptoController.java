package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypto.Des;

public class CryptoController {
    @FXML
    private Button encryptBtn;
    @FXML
    private TextField textTxt;
    @FXML
    private TextField keyTxt;
    @FXML
    private Label criptTextLbl;

    @FXML
    public void onClick() {
        Des des = new Des();
        char[] text = ToTab.toCharTab(textTxt.getText());
        char[] key = ToTab.toCharTab(keyTxt.getText());
        byte[] cipherText;

        cipherText = des.encrypt(text, key);
        criptTextLbl.setText(des.getCipherTextString());
    }
}
