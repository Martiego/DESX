package pl.wtorkowy;

        import javafx.application.Application;
        import javafx.stage.Stage;
        import pl.wtorkowy.crypto.DataBlock;
        import pl.wtorkowy.crypto.KeyBlock;
        import pl.wtorkowy.crypto.Xor;

public class App extends Application {
    public static void main(String[] args) {
        launch();

        System.out.println(Xor.xorCharTab("77777777", "@@@@@@@@", 8));
        DataBlock des = new DataBlock();
        System.out.println(des.toString());


    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
        primaryStage.setTitle("DES");
    }
}
