package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.poi.ss.formula.functions.T;

public class InflationForm extends Form{
    private static Label currentCPILbl, prevCPILbl, currentYear, prevYear;
    private static TextField currentField, prevField, currentYearField, prevYearField;
    private double inflRate, yearlyInflRate;

    public static void clearForm(){
        currentField.setText("");
        prevField.setText("");
        currentYearField.setText("");
        prevYearField.setText("");
    }
    public InflationForm(){
        super();
        currentCPILbl= new Label("Current consumer price index (CPI)");
        prevCPILbl = new Label("Previous CPI");
        currentField= new TextField();
        prevField = new TextField();
        currentYear= new Label("Current year");
        prevYear= new Label("Previous year");
        currentYearField= new TextField("2023");
        prevYearField= new TextField();

        this.add(currentCPILbl, 0, 0);
        this.add(currentField, 1, 0);
        this.add(prevCPILbl, 0, 1);
        this.add(prevField, 1, 1);
        this.add(currentYear, 0, 2);
        this.add(currentYearField, 1, 2);
        this.add(prevYear, 0, 3);
        this.add(prevYearField, 1, 3);

        fields.add(currentField);
        fields.add(prevField);
        fields.add(currentYearField);
        fields.add(prevYearField);
        makeTextFieldsNumeric();
    }
    @Override
    public ObservableList<Row> getData(){
        //If the input is invalid, display an error message and stop the calculation process
        if (!this.validFields()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return null;
        }
        double currentCPI= Double.parseDouble(currentField.getText());
        double previousCPI= Double.parseDouble(prevField.getText());
        double currentYear= Double.parseDouble(currentYearField.getText());
        double prevYear= Double.parseDouble(prevYearField.getText());
        //Initialize the data array
        ObservableList<Row> data = FXCollections.observableArrayList();
        //Set the first row to the previousCPI, the second, to the current.
        data.add(new Row(0, previousCPI));
        data.add(new Row(1, currentCPI));
        this.inflRate= computeInflationRate(currentCPI, previousCPI);
        this.yearlyInflRate= computeYearlyInflationRate(currentCPI, previousCPI, currentYear, prevYear);
        return data;

    }
    @Override
    public boolean validFields(){
        return !currentField.getText().isEmpty() && !prevField.getText().isEmpty();
    }

    private double computeInflationRate(double currentCPI, double previousCPI){
        return (currentCPI-previousCPI)/previousCPI *100.0f;
    }
    private double computeYearlyInflationRate(double currentCPI, double previousCPI, double currentYear, double previousYear){
        double inflationRate= computeInflationRate(currentCPI, previousCPI);
        double deltaYears= currentYear-previousYear;
        return inflationRate / deltaYears;
    }
    @Override
    public String toString(){
        return "inflationForm!";
    }
    public double getInflRate(){
        return this.inflRate;
    }

    public double getYearlyInflRate() {
        return yearlyInflRate;
    }
}
