package WildFoodRegister.controllers;

import WildFoodRegister.DatabaseConnection.ConnectionClass;
import WildFoodRegister.DatabaseConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public LoginController() {

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
    @FXML
    private TextField txtURL;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtHeslo;
    @FXML
    private TextField txtNazev;
    @FXML
    private CheckBox choiceLock;

    public static ObservableList<ConnectionClass> listPripojeni = FXCollections.observableArrayList();

    String url = "";
    String Nazev = "";
    String Heslo = "";
    String Uzivatel = "";

    public void zobrazeniPripojeni(){
        choiceLock.setSelected(true);

        if (choiceLock.isSelected()){
            txtURL.setVisible(false);
            txtHeslo.setVisible(false);
            txtNazev.setVisible(false);
            txtUser.setVisible(false);
        }
        /*

        txtHeslo.setText("Sch00lDatabase");
        txtUser.setText("root");
        txtNazev.setText("wild_food");
        txtURL.setText("jdbc:mysql://localhost/");

         */



        txtHeslo.setText("W1ldF00d");
        txtUser.setText("u296969292_WildFoodAdmin");
        txtNazev.setText("u296969292_WildFoodDB");
        txtURL.setText("jdbc:mysql://109.106.246.51/");



    }

    public void checkBox(){

        if (choiceLock.isSelected()){
            txtURL.setVisible(false);
            txtHeslo.setVisible(false);
            txtNazev.setVisible(false);
            txtUser.setVisible(false);
        } else {
            txtURL.setVisible(true);
            txtHeslo.setVisible(true);
            txtNazev.setVisible(true);
            txtUser.setVisible(true);
        }


    }




    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    public void login() {



        url = txtURL.getText() + txtNazev.getText();
        Heslo = txtHeslo.getText();
        Uzivatel = txtUser.getText();
        Nazev = txtNazev.getText();

        listPripojeni.add(new ConnectionClass(url, Nazev, Heslo, Uzivatel));

        if (username.getText().isBlank() == false && password.getText().isBlank() == false) {
            validateLogin();

        } else {
            loginMessageLabel.setText("Nesprávné jméno, nebo heslo. Prosím zkuste znovu.");
        }

    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin1 = "SELECT count(1) FROM Uzivatel WHERE Username = '" + username.getText() + "' AND Password = '" + password.getText() + "'";


        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin1);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {


                        Stage cancelLoginStage = (Stage) loginButton.getScene().getWindow();
                        Stage stage2 = new Stage();
                        Parent root2 = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                        stage2.setScene(new Scene(root2));
                        stage2.setResizable(false);
                        stage2.setTitle("WildFood Stock Register");
                        stage2.show();
                        cancelLoginStage.close();


                }
                loginMessageLabel.setText("Nesprávné jméno, nebo heslo. Prosím zkuste znovu.");
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zobrazeniPripojeni();

    }

}




