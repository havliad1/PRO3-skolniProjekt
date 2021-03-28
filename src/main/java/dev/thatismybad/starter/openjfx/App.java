package dev.thatismybad.starter.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class which starts everything
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // load of main.fxml
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        stage.setScene(new Scene(root));
        stage.setResizable(true);
        stage.setTitle("Starter JavaFX project");
        stage.show();
    }
}
