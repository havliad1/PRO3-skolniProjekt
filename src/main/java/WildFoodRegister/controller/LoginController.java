package WildFoodRegister.controller;

import WildFoodRegister.ReadAndWrite.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public LoginController(){

    }

    MainController mainController = new MainController();

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

        boolean pripojeni;

        String verifyLogin1 = "SELECT count(1) FROM Manazer WHERE Username = '" + username.getText() + "' AND Password = '" + password.getText() + "'";
        String verifyLogin2 = "SELECT count(1) FROM Zamestnanec WHERE Username = '" + username.getText() + "' AND Password = '" + password.getText() + "'";
        String verifyLogin3 = "SELECT count(1) FROM Delnik WHERE Username = '" + username.getText() + "' AND Password = '" + password.getText() + "'";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin1);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {

                    try {

                        ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Manazer WHERE Username = '" + username.getText() + "'");


                        while (resultSet.next()) {
                            mainController.pridejUzivatele(new Uzivatel(
                                    resultSet.getString("Jmeno"),
                                    resultSet.getString("Prijmeni"),
                                    resultSet.getLong("Telcislo"),
                                    resultSet.getString("Email"),
                                    1
                            ));
                        }

                        Stage cancelLoginStage = (Stage) loginButton.getScene().getWindow();
                        Stage stage2 = new Stage();
                        Parent root2 = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                        stage2.setScene(new Scene(root2));
                        stage2.setResizable(false);
                        stage2.setTitle("WildFood Stock Register");
                        stage2.show();
                        cancelLoginStage.close();



                    } catch (Exception e) {

                    }


                } else {

                    ResultSet queryResult2 = statement.executeQuery(verifyLogin2);

                    while (queryResult2.next()) {
                        if (queryResult2.getInt(1) == 1) {

                            try {

                                ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Zamestnanec WHERE Username = '" + username.getText() + "'");


                                while (resultSet.next()) {
                                    mainController.pridejUzivatele(new Uzivatel(
                                    resultSet.getString("Jmeno"),
                                    resultSet.getString("Prijmeni"),
                                    resultSet.getLong("Telcislo"),
                                    resultSet.getString("Email"),
                                    2
                                    ));
                                }

                                Stage cancelLoginStage = (Stage) loginButton.getScene().getWindow();
                                Stage stage2 = new Stage();
                                Parent root2 = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                                stage2.setScene(new Scene(root2));
                                stage2.setResizable(false);
                                stage2.setTitle("WildFood Stock Register");
                                stage2.show();
                                cancelLoginStage.close();


                            } catch (Exception e) {

                            }

                        } else {

                            ResultSet queryResult3 = statement.executeQuery(verifyLogin3);

                            while (queryResult3.next()) {
                                if (queryResult3.getInt(1) == 1) {

                                    try {

                                        ResultSet resultSet = connectDB.createStatement().executeQuery("SELECT * FROM Delnik WHERE Username = '" + username.getText() + "'");


                                        while (resultSet.next()) {
                                            mainController.pridejUzivatele(new Uzivatel(
                                                    resultSet.getString("Jmeno"),
                                                    resultSet.getString("Prijmeni"),
                                                    resultSet.getLong("Telcislo"),
                                                    resultSet.getString("Email"),
                                                    3
                                            ));
                                        }

                                        Stage cancelLoginStage = (Stage) loginButton.getScene().getWindow();
                                        Stage stage2 = new Stage();
                                        Parent root2 = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                                        stage2.setScene(new Scene(root2));
                                        stage2.setResizable(false);
                                        stage2.setTitle("WildFood Stock Register");
                                        stage2.show();
                                        cancelLoginStage.close();


                                    } catch (Exception e) {

                                    }


                                }
                            }


                            loginMessageLabel.setText("Nesprávné jméno, nebo heslo. Prosím zkuste znovu.");
                        }
                        break;
                    }
                }
                break;
            }

        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }



}
