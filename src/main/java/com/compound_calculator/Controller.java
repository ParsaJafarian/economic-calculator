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

    /**
     * This button is used to either calculate or clear.
     */
    @FXML
    private Button clrBtn, calcBtn;
    /**
     * The tableView that displays the data.
     */
    @FXML
    private TableView<Row> tableView;
    /**
     * The pagination that allows to navigate through the data.
     */
    @FXML
    private Pagination pagination;
    /**
     * The table that manipulates the tableView and pagination.
     */
    private Table table;
    /**
     * The lineChart that displays the graph.
     */
    private LineChart<Number, Number> lineChart;
    /**
     * Container of either form or graph
     */
    @FXML
    private VBox formContainer, graphContainer;
    /**
     * The menuBar that contains the menuItems.
     */
    @FXML
    private MenuBar menuBar;
    private Form form;
    /**
     * ToggleButton that allows to switch between forms
     */
    @FXML
    private ToggleButton compoundTglBtn, presentValueTglBtn, inflationTglBtn;
    /**
     * The results which manipulates the results section
     */
    private Results results;

    @FXML
    public void initialize() {
        //Initialize all needed components
        table = new Table(tableView, pagination);
        lineChart = Graph.getLineChart();
        MenuBarUtils.initializeMenuBar(menuBar, table);
        addForm(new PresentValueForm());
        results = new Results(resultsSection);

        //Add toggleButtons to a group
        ToggleGroup tgg = new ToggleGroup();
        compoundTglBtn.setToggleGroup(tgg);
        presentValueTglBtn.setToggleGroup(tgg);
        inflationTglBtn.setToggleGroup(tgg);

        // Add listeners to the buttons
        calcBtn.setOnAction(e -> calculate());
        clrBtn.setOnAction(e -> clear());
        compoundTglBtn.setOnAction(e -> changeFormType(new CompoundForm()));
        presentValueTglBtn.setOnAction(e -> changeFormType(new PresentValueForm()));
        inflationTglBtn.setOnAction(e -> changeFormType(new InflationForm()));
    }

    /**
     * Add the form to the formContainer.
     *
     * @param form The form to be added to the formContainer
     */
    private void addForm(Form form) {
        formContainer.getChildren().add(form);
        this.form = form;
    }


    /**
     * Change the form type.
     *
     * @param form The form to be added to the formContainer
     */
    private void changeFormType(Form form) {
        //If the form is already displayed, do nothing
        if (formContainer.getChildren().size() < 2) return;
        //Remove the old form and add the new one while clearing components
        clear();
        formContainer.getChildren().remove(1);
        addForm(form);
    }

    /**
     * Clear table, graph, results and form.
     */
    public void clear() {
        table.clear();
        Graph.clear(graphContainer);
        results.clear();
        form.clear();
    }

    /**
     * Display the information alert depending on the form type.
     */
    @FXML
    private void displayMoreInformation() {
        form.displayInformationAlert();
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
