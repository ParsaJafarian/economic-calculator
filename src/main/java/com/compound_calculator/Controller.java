package com.compound_calculator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * Controller class for the main view
 */
public class Controller {

    /**
     * FXML elements imported from index.fxml
     */
    @FXML
    private GridPane compoundForm, resultsSection;
    @FXML
    private Button calcBtn, clrBtn;
    @FXML
    private TableView<Row> tableView;
    @FXML
    private Pagination pagination;
    private Table table;
    private LineChart<Number,Number> lineChart;
    @FXML
    private VBox graphContainer;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label totInterestLabel, totCapitalLabel;
    private Form form;


    /**
     * Initialize the view by adding the options to the frequency combo box,
     * setting the default value to "Monthly", and adding listeners to the
     * buttons.
     */
    @FXML
    public void initialize() {
        table = new Table(tableView, pagination);
        lineChart= Graph.getLineChart();
        form = new Form(compoundForm);
        MenuBarUtils.initializeMenuBar(menuBar, table);

        // Add listeners to the buttons
        calcBtn.setOnAction(e -> calculate());
        clrBtn.setOnAction(e -> clear());
    }

    /**
     * Clear the table and the input fields.
     * This method is called when the clear button is pressed.
     */
    public void clear() {
        //Set table to invisible and select the first option in the frequency combo box
        table.clear();
        if(!graphContainer.getChildren().isEmpty()){
            Node n = graphContainer.getChildren().get(0);
            n.setVisible(false);
            graphContainer.getChildren().remove(n);
        }
        resultsSection.setVisible(false);
        form.clear();
    }

    private void computePresentValueAnnuity(ObservableList<Row> data, int nbPeriods, double annuityPayment, double yieldToMaturity){
        for(int i=1; i<= nbPeriods; i++) {
            double compoundingFactor = 1.0f - 1.0f / Math.pow(1.0f + i, i);
            double presentValue = annuityPayment / yieldToMaturity * compoundingFactor;
            data.add(new Row(i, presentValue));
        }
    }
    private double computeInflationRate(double currentCPI, double previousCPI){
        return (currentCPI-previousCPI)/previousCPI *100.0f;
    }
    private double computeYearlyInflationRate(double currentCPI, double previousCPI, double currentYear, double previousYear){
        double inflationRate= computeInflationRate(currentCPI, previousCPI);
        double deltaYears= currentYear-previousYear;
        double v = inflationRate / deltaYears;
        return v;
    }

    private void setResultsSection(@NotNull ObservableList<Row> data, double yearlyAddition){
        //Calculate the total interest and capital
        final double initInv = data.get(0).getCapital();
        final int years = data.size() - 1;
        final double totCapital = data.get(data.size() - 1).getCapital();
        final double totInterest = totCapital - initInv - yearlyAddition * years;

        //Display the total interest and capital
        totInterestLabel.setText("$" + String.format("%.2f", totInterest));
        totCapitalLabel.setText("$" + String.format("%.2f", totCapital));
        resultsSection.setVisible(true);
    }

    /**
     * Calculate the compound interest and display the results in the table.
     */
    public void calculate() {
        ObservableList<Row> data = form.getData();
        if (data == null) return;
        table.setData(data);
        setResultsSection(data, form.getYearlyAddition());
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
        lineChart= Graph.getLineChart(data);
        graphContainer.getChildren().add(0, lineChart);

    }
}
