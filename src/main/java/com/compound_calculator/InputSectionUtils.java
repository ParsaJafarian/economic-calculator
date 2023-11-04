package com.compound_calculator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

public class InputSection extends GridPane {

    private final TextField initInvField;
    private final TextField yearlyAdditionField;
    private final TextField interestField;
    private final TextField yearsField;
    private final ComboBox<String> freqBox;
    private Table table;

    public InputSection(Table table) {
        super();

        this.table = table;
        this.setAlignment(Pos.CENTER);
        this.setVgap(10.0);
        this.setHgap(10.0);
        this.setPadding(new Insets(20.0, 20.0, 20.0, 20.0));
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-color: #f0f0f0;");

        this.add(new Label("Initial Investment ($)"), 0, 0);

        this.initInvField = new TextField();
        this.add(initInvField, 1, 0);

        this.add(new Label("Yearly addition ($)"), 0, 1);

        this.yearlyAdditionField = new TextField();
        this.add(yearlyAdditionField, 1, 1);

        this.add(new Label("Interest rate (%)"), 0, 2);

        this.interestField = new TextField();
        this.add(interestField, 1, 2);

        this.add(new Label("Compound frequency"), 0, 3);

        this.freqBox = new ComboBox<>();
        this.freqBox.setId("comboBox");
        this.freqBox.getItems().addAll("Monthly", "Quarterly", "Semi-annually", "Yearly");
        this.add(freqBox, 1, 3);

        this.add(new Label("Number of years"), 0, 4);

        this.yearsField = new TextField();
        this.add(yearsField, 1, 4);

        Button clrBtn = new Button("Clear");
        clrBtn.setOnAction(event -> {
            table.setVisible(false);
            this.getChildren().forEach(n -> {
                if (n instanceof TextField textField)
                    textField.clear();
            });
        });
        this.add(clrBtn, 0, 5);

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

        Row[] data = new Row[years + 1];
        Row last = new Row(0, initInv);
        data[0] = last;
        for (int i = 1; i < years * freq + 1; i++) {
            Row curr = new Row(i/freq, last.getCapital() * (1 + interest / freq) + yearlyAddition / freq);
            if (i % freq == 0)
                data[i / freq] = curr;
            last = curr;
        }
        table.setData(data);
        table.setVisible(true);

    }

}
