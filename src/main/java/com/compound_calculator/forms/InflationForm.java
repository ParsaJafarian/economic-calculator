package com.compound_calculator.forms;

import com.compound_calculator.Row;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

public class InflationForm extends Form{
    public InflationForm(GridPane gridPane) {
        super(gridPane);
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean validFields() {
        return false;
    }

    @Override
    public ObservableList<Row> getData() {
        return null;
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
}
