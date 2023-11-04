package com.compound_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
/**
 * Controller class for the main view
 */
public class Controller {

    /**
     * FXML elements imported from index.fxml
     */
    @FXML
    private GridPane inputSection;
    @FXML
    private TableView<Row> table;
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
    private Button excelBtn;

    /**
     * Initialize the view by adding the options to the frequency combo box,
     * setting the default value to "Monthly", and adding listeners to the
     * buttons.
     */
    @FXML
    public void initialize() {
        // Add options to the frequency combo box
        freqBox.getItems().addAll("Monthly", "Quarterly", "Semi-annually", "Yearly");
        freqBox.getSelectionModel().selectFirst();

        // Add listeners to the buttons
        calcBtn.setOnAction(e -> calculate());
        clrBtn.setOnAction(e -> clear());
        excelBtn.setOnAction(e -> TableUtils.exportToExcel(table));

        // Initialize the table & make text fields numeric
        TableUtils.initializeTable(table);
        InputSectionUtils.makeTextFieldsNumeric(inputSection);
    }

    /**
     * Clear the table and the input fields.
     * This method is called when the clear button is pressed.
     */
    private void clear() {
        //Set table to invisible and select the first option in the frequency combo box
        table.setVisible(false);
        excelBtn.setVisible(false);
        freqBox.getSelectionModel().selectFirst();
        //Clear all text fields
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField)
                textField.setText("");
        });
    }

    /**
     * Calculate the compound interest and display the results in the table.
     */
    @FXML
    public void calculate() {

        //If the input is invalid, display an error message and stop the calculation process
        if (!InputSectionUtils.validFields(inputSection)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        //Get the input from the text fields
        double initInv = Double.parseDouble(initInvField.getText());
        double yearlyAddition = Double.parseDouble(yearlyAdditionField.getText());
        double interest = Double.parseDouble(interestField.getText()) / 100;
        int years = Integer.parseInt(yearsField.getText());


        //Get the frequency from the combo box
        int freq = switch (freqBox.getValue()) {
            case "Monthly" -> 12;
            case "Quarterly" -> 4;
            case "Semi-annually" -> 2;
            case "Yearly" -> 1;
            default -> 0;
        };

        //Initialize the data array (note: the size is years + 1 because the first row is the initial investment)
        Row[] data = new Row[years + 1];
        //last is a variable that points to the last row inserted into the data array
        Row last = new Row(0, initInv);
        //Set the first row to the initial investment
        data[0] = last;

        //Loop through the years and calculate the compound interest
        for (int i = 1; i < years * freq + 1; i++) {
            Row curr = new Row(i / freq, last.getCapital() * (1 + interest / freq) + yearlyAddition / freq);
            //If the current amount of time is a multiple of the frequency, add the row to the data array
            if (i % freq == 0)
                data[i / freq] = curr;
            //Set last to the current row
            last = curr;
        }
        //Set the data in the table to the data array and make the table visible
        TableUtils.setTabularData(table, data);
        table.setVisible(true);
        excelBtn.setVisible(true);
    }
}
