package WildFoodRegister.controller;

import WildFoodRegister.App;
import WildFoodRegister.ReadAndWrite.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    public App app;

    public LoginController(){
        this.app = new App();

    }

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;




    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void login() {
        if (username.getText().isBlank() == false && password.getText().isBlank() == false){
            validateLogin();


        }

    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM Manazer WHERE Username = '" + username.getText() + "' AND Password = '" + password.getText() + "'";
     //   String verifyLogin = "SELECT count(1) FROM Manazer WHERE Username = 'Admin' AND Password = 'Admin'";

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
                        stage2.setResizable(false);
                        stage2.setTitle("WildFood Stock Register");
                        stage2.show();
                        cancelLoginStage.close();



                    }catch (Exception e){
                        System.out.println("Nepodarilo se zobrazit main okno");
                    }



                } else {

                }

            }


        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }






}
