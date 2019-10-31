package pl.wtorkowy;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.layout.StackPane;
        import javafx.stage.Stage;
        import pl.wtorkowy.cast.ToTab;
        import pl.wtorkowy.crypto.*;

        import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch();

        char[] text = ToTab.toCharTab("pies to jest test");
        char[] key = ToTab.toCharTab("kuleczkaxd");
        char[] internalKey = ToTab.toCharTab("132");
        char[] externalKey = ToTab.toCharTab("4444");
        byte[] cipherText;
        byte[] decipherText;

        Desx desx = new Desx();
        cipherText = desx.encrypt(text, internalKey, key, externalKey);
        ToTab.show(cipherText, "Zaszyfrowane", 8);

        decipherText = desx.decrypt(cipherText, internalKey, key, externalKey);
        ToTab.show(decipherText, "Odszyfrowane", 8);
        System.out.println(desx.getDecipherTextString());

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane, 600, 400);

        primaryStage.setTitle("DES");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
