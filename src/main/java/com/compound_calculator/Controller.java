package com.compound_calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    public TextField initialInvestment;
    public TextField yearlyAddition;
    public TextField interestRate;
    public ComboBox comboBox;
    public TextField numberOfYears;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void calculate(ActionEvent actionEvent) {
    }
}