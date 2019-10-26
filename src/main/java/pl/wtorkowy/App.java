package pl.wtorkowy;

        import javafx.application.Application;
        import javafx.stage.Stage;
        import pl.wtorkowy.cast.ToTab;
        import pl.wtorkowy.crypto.DataBlock;
        import pl.wtorkowy.crypto.Des;
        import pl.wtorkowy.crypto.KeyBlock;
        import pl.wtorkowy.crypto.Xor;

        import java.util.Arrays;

public class App extends Application {
    public static void main(String[] args) {
        launch();

//        DataBlock des = new DataBlock();
//        System.out.println(des.toString());
//Test Jednostkowy
//        Des desx = new Des();
//        System.out.println("" + desx.toInt(new byte[] { '\1', '\0', '\1' }));
//Przeszedl xdddddd

        Des des = new Des(ToTab.toCharTab("aaaabbbb"), ToTab.toCharTab("32aabbbb"));
        des.encrypt();
        System.out.println(Arrays.toString(des.getCipherText()) + "\nLength: " + des.getCipherText().length);


    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
        primaryStage.setTitle("DES");
    }
}
