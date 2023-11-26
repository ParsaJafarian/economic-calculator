package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InflationForm extends Form{
    private final TextField currentField, prevField, currentYearField, prevYearField;
    private double inflRate, yearlyInflRate;

    public void clear(){
        currentField.setText("");
        prevField.setText("");
        currentYearField.setText("");
        prevYearField.setText("");
    }
    public InflationForm(){
        super();
        Label currentCPILbl= new Label("Current consumer price index (CPI)");
        Label prevCPILbl = new Label("Previous CPI");
        currentField= new TextField();
        prevField = new TextField();
        Label currentYear= new Label("Current year");
        Label prevYear= new Label("Previous year");
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

     static double computeInflationRate(double currentCPI, double previousCPI){
        return (currentCPI-previousCPI)/previousCPI *100.0f;
    }
     static double computeYearlyInflationRate(double currentCPI, double previousCPI, double currentYear, double previousYear){
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

    public static void displayInformationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inflation");
        alert.setHeaderText("How it works");
        alert.setContentText("Inflation is the continuous increase in prices, diminishing purchasing power," +
                " influenced by factors such as demand spikes, supply disruptions, or excess money circulation." +
                " To learn more, visit https://www.imf.org/en/Publications/fandd/issues/Series/Back-to-Basics/Inflation");
        alert.showAndWait();
    }

    public double getYearlyInflRate() {
        return yearlyInflRate;
    }
}
