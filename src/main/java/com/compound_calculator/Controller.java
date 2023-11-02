package com.compound_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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
        excelBtn.setOnAction(e -> exportToExcel());

        // Initialize the table & make text fields numeric
        initializeTable();
        makeTextFieldsNumeric();
    }

    /**
     * Initialize the table by adding the columns.
     * Two columns are added: Time (years) and Capital ($)
     */
    private void initializeTable() {
        // Make the columns
        TableColumn<Integer, String> timeCol = new TableColumn<>("Time (years)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Double, String> capitalCol = new TableColumn<>("Capital ($)");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        // Add the columns to the table
        table.getColumns().add(timeCol);
        table.getColumns().add(capitalCol);
    }

    /**
     * Make all text fields in the input section numeric by
     * looping through all children of the input section.
     * If the child is a text field, add a listener to it
     * that will only allow numeric input.
     */
    private void makeTextFieldsNumeric() {
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField) {
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    //If newly typed string is not numeric, replace it with an empty string
                    //This is done to avoid having to check if the string is numeric later on
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("\\D", ""));
                    }
                });
            }
        });
    }

    /**
     * Clear the table and the input fields.
     * This method is called when the clear button is pressed.
     */
    private void clear() {
        //Set table to invisible and select the first option in the frequency combo box
        table.setVisible(false);
        freqBox.getSelectionModel().selectFirst();
        //Clear all text fields
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField)
                textField.setText("");
        });
    }

    /**
     * @return true if all text fields in the input section are filled in, false otherwise
     * Used to check if the input is valid before calculating
     */
    private boolean validFields() {
        return inputSection.getChildren().stream().allMatch(n -> {
            if (n instanceof TextField textField)
                return !textField.getText().isEmpty();
            return true;
        });
    }

    /**
     * Set the data in the table to the given data.
     * This method is called when the calculate button is pressed.
     * @param data the data to be displayed in the table. It is an array of Row objects that contain the time and capital
     */
    private void setTabularData(@NotNull Row @NotNull [] data) {
        table.getItems().clear();
        for (Row row : data)
            table.getItems().add(row);
    }

    /**
     * Calculate the compound interest and display the results in the table.
     */
    @FXML
    public void calculate() {

        //If the input is invalid, display an error message and stop the calculation process
        if (!validFields()) {
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
        setTabularData(data);
        table.setVisible(true);
    }

    private void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as Excel file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel file", "*.xlsx"));
        fileChooser.setInitialFileName("data.xlsx");

        File file = fileChooser.showOpenDialog(table.getScene().getWindow());


    }
}
