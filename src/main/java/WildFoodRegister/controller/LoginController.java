package WildFoodRegister.controller;

import WildFoodRegister.ReadAndWrite.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    public LoginController(){

    }

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label loginMessageLabel;

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void login() {
        if (username.getText().isBlank() == false && password.getText().isBlank() == false){
            validateLogin();
        } else {
            loginMessageLabel.setText("Nesprávné jméno, nebo heslo. Prosím zkuste znovu.");
        }

    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM Manazer WHERE Username = '" + username.getText() + "' AND Password = '" + password.getText() + "'";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if (queryResult.getInt(1) == 1){

                    try {
                        Stage cancelLoginStage = (Stage) loginButton.getScene().getWindow();
                        Stage stage2 = new Stage();
                        Parent root2 = FXMLLoader.load(getClass().getResource("/view/main.fxml"));




                        stage2.setScene(new Scene(root2));
                        stage2.setResizable(true);
                        stage2.setTitle("WildFood Stock Register");
                        stage2.show();
                        cancelLoginStage.close();

                    }catch (Exception e){

                    }

                } else {
                    loginMessageLabel.setText("Nesprávné jméno, nebo heslo. Prosím zkuste znovu.");
                }

            }


        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

}
