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
        Des des = new Des(ToTab.toCharTab(textTxt.getText()), ToTab.toCharTab(keyTxt.getText()));
        des.encrypt();
        criptTextLbl.setText(des.getCipherTextString());
    }
}
