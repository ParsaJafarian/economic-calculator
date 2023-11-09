package com.compound_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.compound_calculator.Table.Row;

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
    private TableView<Row> tableView;
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
    private Slider yearsSlider;
    @FXML
    private Pagination pagination;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label totInterestLabel;
    @FXML
    private Label totCapitalLabel;
    @FXML
    private GridPane resultsSection;
    private Table table;

    /**
     * Initialize the view by adding the options to the frequency combo box,
     * setting the default value to "Monthly", and adding listeners to the
     * buttons.
     */
    @FXML
    public void initialize() {
        // Add options to the frequency combo box
        freqBox.getItems().addAll("Yearly", "Semi-annually", "Quarterly", "Monthly");
        freqBox.getSelectionModel().selectFirst();

        // Initialize the table & make text fields numeric
        table = new Table(tableView, pagination);
        //Tableview and pagination are null because they should only be accessed through the table object
        tableView = null;
        pagination = null;

        //Initialize the input section
        InputSectionUtils.initializeInputSection(inputSection, interestField);
        // Initialize the menu bar
        MenuBarUtils.initializeMenuBar(menuBar, table);

        // Add listeners to the buttons
        calcBtn.setOnAction(e -> calculate());
        clrBtn.setOnAction(e -> clear());
    }

    /**
     * Clear the table and the input fields.
     * This method is called when the clear button is pressed.
     */
    private void clear() {
        //Set table to invisible and select the first option in the frequency combo box
        table.clear();
        resultsSection.setVisible(false);
        freqBox.getSelectionModel().selectFirst();
        //Clear all text fields
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField)
                textField.setText("");
        });
        yearsSlider.setValue(0);
    }

    /**
     * Calculate the compound interest and display the results in the table.
     */
    //@TODO: correct the calculation
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
        //Get the frequency from the combo box
        final int freq = switch (freqBox.getValue()) {
            case "Monthly" -> 12;
            case "Quarterly" -> 4;
            case "Semi-annually" -> 2;
            case "Yearly" -> 1;
            default -> 0;
        };

        //Get the input from the text fields
        final double initInv = Double.parseDouble(initInvField.getText());
        final double yearlyAddition = Double.parseDouble(yearlyAdditionField.getText());
        final double interest = Double.parseDouble(interestField.getText()) / 100;
        final int years = (int) yearsSlider.getValue();

        //Initialize the data array (note: the size is years + 1 because the first row is the initial investment)
        ObservableList<Row> data = FXCollections.observableArrayList();
        //Set the first row to the initial investment
        data.add(new Row(0, initInv));

        //Loop through the years and calculate the compound interest
        //capital = lastCapital*(1 + i/n)^n + yearlyAddition
        for (int i = 1; i <= years; i++) {
            double lastCapital = data.get(i-1).getCapital();
            double capital = lastCapital * Math.pow(1 + interest / freq, freq) + yearlyAddition;
            data.add(new Row(i, capital));
        }
        //Pass in a copy of the data to the table so that controller doesn't have access to table's data
        table.setData(FXCollections.observableArrayList(data));

        //Calculate the total interest and capital
        double totCapital = data.get(data.size() - 1).getCapital();
        double totInterest = totCapital - initInv - yearlyAddition * years;

        //Display the total interest and capital
        totInterestLabel.setText("$" + String.format("%.2f", totInterest));
        totCapitalLabel.setText("$" + String.format("%.2f", totCapital));
        resultsSection.setVisible(true);
    }
}
