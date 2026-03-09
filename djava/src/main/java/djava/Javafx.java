package djava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Javafx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello JavaFX");

        Scene scene = new Scene(label, 800, 400);

        stage.setTitle("Test Window");
        stage.setScene(scene);
        stage.show();   // THIS displays the window
    }
}