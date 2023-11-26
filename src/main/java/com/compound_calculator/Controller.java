package com.compound_calculator;

import com.compound_calculator.form.CompoundForm;
import com.compound_calculator.form.Form;
import com.compound_calculator.form.InflationForm;
import com.compound_calculator.form.PresentValueForm;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Controller class for the main view
 */
public class Controller {

    /**
     * FXML elements imported from index.fxml
     */
    @FXML
    private GridPane resultsSection;

    @FXML
    private Button clrBtn, calcBtn;
    @FXML
    private TableView<Row> tableView;
    @FXML
    private Pagination pagination;
    private Table table;
    private LineChart<Number, Number> lineChart;
    @FXML
    private VBox formContainer, graphContainer;
    @FXML
    private MenuBar menuBar;
    private Form form;
    @FXML
    private ToggleButton compoundTglBtn, presentValueTglBtn, inflationTglBtn;
    private Results results;

    /**
     * Initialize the view by adding the options to the frequency combo box,
     * setting the default value to "Monthly", and adding listeners to the
     * buttons.
     */
    @FXML
    public void initialize() {
        table = new Table(tableView, pagination);
        lineChart = Graph.getLineChart();
        MenuBarUtils.initializeMenuBar(menuBar, table);
        addForm(new CompoundForm());
        results = new Results(resultsSection);

        // Add listeners to the buttons
        calcBtn.setOnAction(e -> calculate());
        clrBtn.setOnAction(e -> clear());
        ToggleGroup tgg = new ToggleGroup();
        compoundTglBtn.setToggleGroup(tgg);
        presentValueTglBtn.setToggleGroup(tgg);
        inflationTglBtn.setToggleGroup(tgg);
        //Add functionality to toggleButtons
        compoundTglBtn.setOnAction(e -> changeFormType(new CompoundForm()));
        presentValueTglBtn.setOnAction(e -> changeFormType(new PresentValueForm()));
        inflationTglBtn.setOnAction(e -> changeFormType(new InflationForm()));
    }

    private void addForm(Form form) {
        formContainer.getChildren().add(form);
        this.form = form;
    }


    private void changeFormType(Form form) {
        if (formContainer.getChildren().size() < 2) return;
        clear();
        formContainer.getChildren().remove(1);
        addForm(form);
        resultsSection.setVisible(false);
    }

    /**
     * Clear the table and the input fields.
     * This method is called when the clear button is pressed.
     */
    public void clear() {
        table.clear();
        Graph.clear(graphContainer);
        results.clear();
        form.clear();
    }

    @FXML
    private void displayMoreInformation() {
        if (this.form instanceof CompoundForm)
            CompoundForm.displayInformationAlert();
        else if (this.form instanceof InflationForm)
            InflationForm.displayInformationAlert();
        else if (this.form instanceof PresentValueForm)
            PresentValueForm.displayInformationAlert();
    }

    /**
     * Calculate the compound interest and display the results in the table.
     */
    public void calculate() {
        results.clear();
        System.out.println(this.form.getClass());
        ObservableList<Row> data = form.getData();

        if (data == null) return;
        table.setData(data);
        Graph.clear(graphContainer);

        if (this.form instanceof CompoundForm) {
            CompoundForm f = (CompoundForm) form;
            results.setToCpdIntr(data, f.getYearlyAddition());
            addLineChart(data, "Compound Interest");
        } else if (this.form instanceof InflationForm) {
            InflationForm infF = (InflationForm) form;
            addLineChart(data, "Inflation");
            results.setToInfl(infF.getInflRate(), infF.getYearlyInflRate());
        } else if (this.form instanceof PresentValueForm) {
            PresentValueForm pVF = (PresentValueForm) form;
            addLineChart(data, "Present Value");
            results.setToPresVal(pVF.getPresentValue(), pVF.getLostToInflation());
        }
    }

    private void addLineChart(ObservableList<Row> data, String title) {
        //the '0' in the line below makes sure of the fact that the graph is added to the top of the VBox
        lineChart = Graph.getLineChart(data, title);
        graphContainer.getChildren().add(0, lineChart);
    }

}
