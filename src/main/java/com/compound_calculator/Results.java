package com.compound_calculator;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class Results {
    private final GridPane resultsSection;
    private static final DecimalFormat dollarFormat = new DecimalFormat("0.00");


    public Results(GridPane resultsSection) {
        this.resultsSection = resultsSection;
    }


    public void setToCpdIntr(@NotNull ObservableList<Row> data, double yearlyAddition) {
        //Calculate the total interest and capital
        final double initInv = data.get(0).getCapital();
        final int years = data.size() - 1;
        final double totCapital = data.get(data.size() - 1).getCapital();
        final double totInterest = totCapital - initInv - yearlyAddition * years;

        resultsSection.add(new Label("Total value of your investment"), 0, 0);
        resultsSection.add(new Label("Total interest earned"), 0, 1);
        resultsSection.add(new Label(dollarFormat.format(totCapital)), 1, 0);
        resultsSection.add(new Label(dollarFormat.format(totInterest)), 1, 1);

        resultsSection.setVisible(true);
    }

    public void setToInfl(double infl, double yInfl) {
        resultsSection.add(new Label("Inflation rate"), 0, 0);
        resultsSection.add(new Label("Yearly inflation rate"), 0, 1);
        resultsSection.add(new Label(dollarFormat.format(infl) + "%"), 1, 0);
        resultsSection.add(new Label(dollarFormat.format(yInfl) + "%"), 1, 1);
        resultsSection.setVisible(true);
    }

    public void setToPresVal(double presentValue, double lostToInflation) {
        resultsSection.add(new Label("Present value"), 0, 0);
        resultsSection.add(new Label(dollarFormat.format(presentValue)), 1, 0);
        resultsSection.add(new Label("Lost to inflation"), 0, 1);
        resultsSection.add(new Label(dollarFormat.format(lostToInflation)), 1, 1);
        resultsSection.setVisible(true);
    }

    public void clear() {
        resultsSection.getChildren().forEach(n -> n.setVisible(false));
        resultsSection.setVisible(false);
    }
}
