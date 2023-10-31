package com.compound_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private GridPane inputSection;
    @FXML
    private TableView table;
    @FXML
    private Button calcBtn;
    @FXML
    private Button clrBtn;
    @FXML
    private ComboBox<String> freqBox;
    @FXML
    private TextField interestField;
    @FXML
    private TextField initInvField;
    @FXML
    private TextField yearlyAdditionField;
    @FXML
    private TextField yearsField;

    @FXML
    public void initialize() {
        freqBox.getItems().addAll("Monthly", "Quarterly", "Semi-annually", "Yearly");
        freqBox.getSelectionModel().selectFirst();

        calcBtn.setOnAction(event -> calculate());
        clrBtn.setOnAction(event -> clear());

        initializeTable();
        makeTextFieldsNumeric();
    }

    private void initializeTable() {
        TableColumn<Integer, String> timeCol = new TableColumn<>("Time (years)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Double, String> capitalCol = new TableColumn<>("Capital ($)");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        table.getColumns().add(timeCol);
        table.getColumns().add(capitalCol);
    }

    private void makeTextFieldsNumeric() {
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField) {
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("\\D", ""));
                    }
                });
            }
        });
    }

    private void clear() {
        table.setVisible(false);
        freqBox.getSelectionModel().selectFirst();
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField)
                textField.setText("");
        });
    }

    private boolean validFields() {
        return inputSection.getChildren().stream().allMatch(n -> {
            if (n instanceof TextField textField)
                return !textField.getText().isEmpty();
            return true;
        });
    }

    private void setTabularData(Row[] data) {
        table.getItems().clear();
        for (Row row : data)
            table.getItems().add(row);
    }

    @FXML
    public void calculate() {

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
            Row curr = new Row(i / freq, last.getCapital() * (1 + interest / freq) + yearlyAddition / freq);
            if (i % freq == 0)
                data[i / freq] = curr;
            last = curr;
        }
        setTabularData(data);
        table.setVisible(true);
    }
}
