package com.compound_calculator.forms;

import com.compound_calculator.Row;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

public class AnnuityForm extends Form{
    public AnnuityForm(GridPane gridPane) {
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

    private void computePresentValueAnnuity(ObservableList<Row> data, int nbPeriods, double annuityPayment, double yieldToMaturity){
        for(int i=1; i<= nbPeriods; i++) {
            double compoundingFactor = 1.0f - 1.0f / Math.pow(1.0f + i, i);
            double presentValue = annuityPayment / yieldToMaturity * compoundingFactor;
            data.add(new Row(i, presentValue));
        }
    }
}
