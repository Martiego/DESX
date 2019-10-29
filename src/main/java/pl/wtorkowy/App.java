package pl.wtorkowy;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.layout.StackPane;
        import javafx.stage.Stage;
        import pl.wtorkowy.cast.ToTab;
        import pl.wtorkowy.crypto.DataBlock;
        import pl.wtorkowy.crypto.Des;
        import pl.wtorkowy.crypto.KeyBlock;
        import pl.wtorkowy.crypto.Xor;

        import java.io.IOException;
        import java.util.Arrays;

public class App extends Application {
    public static void main(String[] args) {
        launch();

        char[] text = ToTab.toCharTab("aaaaaaaa");
        char[] key = ToTab.toCharTab("aaaaaaaa");
        byte[] cipherText;
        byte[] decrypt;

        Des des = new Des();
        cipherText = des.encrypt(text, key);
        System.out.println(des.getCipherTextString());

        for (byte b: cipherText) {
            System.out.print(b + ", ");
        }

        decrypt = des.decrypt(cipherText, key);
        System.out.println(des.getCipherTextString());

        for (byte b: decrypt) {
            System.out.print(b + ", ");
        }
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
