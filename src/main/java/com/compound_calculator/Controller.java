package com.compound_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

/**
 * Controller class for the main view
 */
public class Controller {

    /**
     * FXML elements imported from index.fxml
     */
    @FXML
    private GridPane inputSection, resultsSection;
    @FXML
    private ComboBox<String> freqBox;
    @FXML
    private TextField interestField, initInvField, yearlyAdditionField;
    @FXML
    private Slider yearsSlider;
    @FXML
    private Button calcBtn, clrBtn;
    @FXML
    private TableView<Row> tableView;
    @FXML
    private Pagination pagination;
    private Table table;
    @FXML
    private VBox graphContainer;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label totInterestLabel, totCapitalLabel;


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
    public ObservableList<Row> extractDataFromForm(){
        //If the input is invalid, display an error message and stop the calculation process
        if (!InputSectionUtils.validFields(inputSection)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return null;
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
            double lastCapital = data.get(i - 1).getCapital();
            double capital = lastCapital * Math.pow(1 + interest / freq, freq) + yearlyAddition;
            data.add(new Row(i, capital));
        }
        setResultsSection(data, initInv, yearlyAddition, years);
        return data;
    }
    private void setResultsSection(ObservableList<Row> data, double initInv, double yearlyAddition, int years){
        //Calculate the total interest and capital
        double totCapital = data.get(data.size() - 1).getCapital();
        double totInterest = totCapital - initInv - yearlyAddition * years;

        //Display the total interest and capital
        totInterestLabel.setText("$" + String.format("%.2f", totInterest));
        totCapitalLabel.setText("$" + String.format("%.2f", totCapital));
        resultsSection.setVisible(true);
    }

    /**
     * Calculate the compound interest and display the results in the table.
     */
    public void calculate() {

        ObservableList<Row> data = extractDataFromForm();
        table.setData(data);
        //Creates and adds new Line Chart with chosen data to appropriate VBox container named "graphContainer"
        if(!graphContainer.getChildren().isEmpty()){
            //If a graph was already generated, and the user wishes to generate a new one,
            //everything is removed form graphContainer to allow a new graph to be added.
            //Since there can only be one chart at a time, we only need to remove element 0 form the container.
            //We set it to be invisible first, because otherwise the graphics don't update, and it stays on the screen
            //despite having been deleted.
            Node n= graphContainer.getChildren().get(0);
            n.setVisible(false);
            graphContainer.getChildren().remove(n);

        }
        addLineChart(data);

    }
    private void addLineChart(ObservableList<Row> data){
        //the '0' in the line below makes sure of the fact that the graph is added to the top of the VBox
        graphContainer.getChildren().add(0, Graph.getLineChart(data));
    }
}
