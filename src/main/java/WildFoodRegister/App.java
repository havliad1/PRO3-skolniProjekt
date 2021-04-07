package WildFoodRegister;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class which starts everything
 */
public class App extends Application {

    Stage stage1;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // načtení login screenu

       // Parent root1 = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage1 = new Stage();

        stage1.setScene(new Scene(root2));
        stage1.setResizable(false);
        stage1.setTitle("WildFood login");
        stage1.show();



    }
}
