package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InflationForm extends Form {
    private final TextField currentField, prevField, currentYearField, prevYearField;
    private double inflRate, yearlyInflRate;

    public void clear() {
        currentField.setText("");
        prevField.setText("");
        currentYearField.setText("");
        prevYearField.setText("");
    }

    /**
     *<h1>Inflation Form</h1>
     *<h2>This class extends Form() and allows to specify all the necessary fields that are required to make the 'Inflation' form</h2>
     *<h3>These fields include: current CPI, previous CPI, current Year, previous Year</h3>
     */
    public InflationForm() {
        super();

        //instantiating all the labels and fields that comprise this form
        Label currentCPILbl = new Label("Current consumer price index (CPI)");
        Label prevCPILbl = new Label("Previous CPI");

        currentField = new TextField();
        prevField = new TextField();
        Label currentYear = new Label("Current year");
        Label prevYear = new Label("Previous year");
        currentYearField = new TextField("2023");
        prevYearField = new TextField();
        //adding them to the GridPane which this form extends
        this.add(currentCPILbl, 0, 0);
        this.add(currentField, 1, 0);
        this.add(prevCPILbl, 0, 1);
        this.add(prevField, 1, 1);
        this.add(currentYear, 0, 2);
        this.add(currentYearField, 1, 2);
        this.add(prevYear, 0, 3);
        this.add(prevYearField, 1, 3);
        //adding the textFields to fields to set them to be numeric
        fields.add(currentField);
        fields.add(prevField);
        fields.add(currentYearField);
        fields.add(prevYearField);
        makeTextFieldsNumeric();
    }

    @Override
    public ObservableList<Row> getData() {
        //If the input is invalid, display an error message and stop the calculation process
        if (!this.validFields()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return null;
        }
        double currentCPI = Double.parseDouble(currentField.getText());
        double previousCPI = Double.parseDouble(prevField.getText());
        double currentYear = Double.parseDouble(currentYearField.getText());
        double prevYear = Double.parseDouble(prevYearField.getText());
        //Initialize the data array
        ObservableList<Row> data = FXCollections.observableArrayList();
        //Set the first row to the previousCPI, the second, to the current.
        data.add(new Row(0, previousCPI));
        data.add(new Row(1, currentCPI));
        this.inflRate = computeInflationRate(currentCPI, previousCPI);
        this.yearlyInflRate = computeYearlyInflationRate(currentCPI, previousCPI, currentYear, prevYear);
        return data;
    }

    @Override
    public boolean validFields() {
        //if any of the required fields are empty, the calculation cannot be made, and we return false
        return !currentField.getText().isEmpty() && !prevField.getText().isEmpty();
    }

    /**
     * <h1>the purpose of this function is to compute the inflation rate<h1/>
     * @param currentCPI current consumer price index
     * @param previousCPI previous consumer price index
     * @return inflation rate
     *
     */
    static double computeInflationRate(double currentCPI, double previousCPI) {
        //computing the inflation (in %)
        return (currentCPI - previousCPI) / previousCPI * 100.0f;
    }

    /**
     *<h1>the purpose of this function is to compute the yearly inflation rate</h1>
     * @param currentCPI current consumer price index
     * @param previousCPI previous consumer price index
     * @param currentYear current year
     * @param previousYear previous year
     * @return yearly inflation rate
     */
    static double computeYearlyInflationRate(double currentCPI, double previousCPI, double currentYear, double previousYear) {
        //computing the yearly inflation, by dividing the inflation by the amount of years
        double inflationRate = computeInflationRate(currentCPI, previousCPI);
        double deltaYears = currentYear - previousYear;
        return inflationRate / deltaYears;
    }

    @Override
    public String toString() {
        return "inflationForm!";
    }

    public double getInflRate() {
        return this.inflRate;
    }

    public static void displayInformationAlert() {
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
