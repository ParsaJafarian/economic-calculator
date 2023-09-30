package com.compound_calculator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class InputSection extends GridPane {

    TextField initInvField;
    TextField yearlyAdditionField;
    TextField interestField;
    TextField yearsField;
    ComboBox<String> freqBox;

    public InputSection() {
        this.setAlignment(Pos.CENTER);
        this.setVgap(10.0);
        this.setHgap(10.0);
        this.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));

        this.add(new Label("Initial Investment ($)"), 0, 0);

        initInvField = new TextField();
        this.add(initInvField, 1, 0);

        this.add(new Label("Yearly addition ($)"), 0, 1);

        yearlyAdditionField = new TextField();
        this.add(yearlyAdditionField, 1, 1);

        this.add(new Label("Interest rate (%)"), 0, 2);

        interestField = new TextField();
        this.add(interestField, 1, 2);

        this.add(new Label("Compound frequency"), 0, 3);

        freqBox = new ComboBox<>();
        freqBox.setId("comboBox");
        freqBox.getItems().addAll("Monthly", "Quarterly", "Semi-annually", "Yearly");
        this.add(freqBox, 1, 3);

        this.add(new Label("Number of years"), 0, 4);

        yearsField = new TextField();
        this.add(yearsField, 1, 4);

        Button calcBtn = new Button("Calculate");
        calcBtn.setOnAction(event -> calculate());
        this.add(calcBtn, 1, 5);

        makeTextFieldsNumeric();
    }

    private void makeTextFieldsNumeric() {
        this.getChildren().forEach(n -> {
            if (n instanceof TextField textField) {
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("\\D", ""));
                    }
                });
            }
        });
    }

    private boolean validFields() {
        return this.getChildren().stream().allMatch(n -> {
            if (n instanceof TextField textField)
                return !textField.getText().isEmpty();
            return true;
        });
    }

    private void calculate() {

        if (!validFields()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        double initInv = Double.parseDouble(initInvField.getText());
        double yearlyAddition = Double.parseDouble(yearlyAdditionField.getText());
        double interest = Double.parseDouble(interestField.getText()) / 100;
        int years = Integer.parseInt(yearsField.getText());
        int freq = switch (freqBox.getValue()) {
            case "Monthly" -> 12;
            case "Quarterly" -> 4;
            case "Semi-annually" -> 2;
            case "Yearly" -> 1;
            default -> 0;
        };

        double[] data = new double[years * freq + 1];
        data[0] = initInv;
        for (int i = 1; i < data.length; i++)
            data[i] = data[i - 1] * (1 + interest / freq) + yearlyAddition / freq;



    }

}
