package com.compound_calculator.form;
import com.compound_calculator.Row;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class PresentValueForm extends Form {
    public PresentValueForm(){

    }
    private void computePresentValueAnnuity(ObservableList<Row> data, int nbPeriods, double annuityPayment, double yieldToMaturity){
        for(int i=1; i<= nbPeriods; i++) {
            double compoundingFactor = 1.0f - 1.0f / Math.pow(1.0f + i, i);
            double presentValue = annuityPayment / yieldToMaturity * compoundingFactor;
            data.add(new Row(i, presentValue));
        }
    }
    @Override
    public String toString(){
        return "presentValueForm!";
    }

}
