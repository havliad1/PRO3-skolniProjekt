package dev.thatismybad.starter.openjfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class represents main controller for business logic and work with UI
 */
public class MainController implements Initializable {

    private int counter = 0;

    @FXML
    private Label counterLabel;

    public void clickMeButton() {
        counter++;
        updateCounterLabel();
    }

    private void updateCounterLabel() {
        counterLabel.setText(String.format("You click on me %d times!", counter));
    }


    /**
     * Method for initialization of components - it's like constructor of the class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateCounterLabel();
    }
}
