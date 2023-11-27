package com.compound_calculator;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

/**
 * This class is used to display the results of the calculations in each form.
 * It does so by manipulating the GridPane that contains the results.
 */
public class Results {
    private final GridPane resultsSection;
    /**
     * This is the format used to display the results.
     */
    private static final DecimalFormat dollarFormat = new DecimalFormat("0.00");


    /**
     * @param resultsSection The GridPane that contains the results
     */
    public Results(GridPane resultsSection) {
        this.resultsSection = resultsSection;
    }


    /**
     * This method is used to display the results of the compound interest form.
     * @param data The data coming from the table
     * @param yearlyAddition The yearly addition
     */
    public void setToCpdIntr(@NotNull ObservableList<Row> data, double yearlyAddition) {
        //Calculate the total interest and capital
        final double initInv = data.get(0).getCapital();
        final int years = data.size() - 1;
        final double totCapital = data.get(data.size() - 1).getCapital();
        final double totInterest = totCapital - initInv - yearlyAddition * years;

        resultsSection.add(new Label("Total value of your investment"), 0, 0);
        resultsSection.add(new Label("Total interest earned"), 0, 1);
        resultsSection.add(new Label("$"+dollarFormat.format(totCapital)), 1, 0);
        resultsSection.add(new Label("$"+dollarFormat.format(totInterest)), 1, 1);

        resultsSection.setVisible(true);
    }

    /**
     * This method is used to display the results of the inflation form.
     * @param infl The inflation rate
     * @param yInfl The yearly inflation rate
     */
    public void setToInfl(double infl, double yInfl) {
        resultsSection.add(new Label("Inflation rate"), 0, 0);
        resultsSection.add(new Label("Yearly inflation rate"), 0, 1);
        resultsSection.add(new Label(dollarFormat.format(infl) + "%"), 1, 0);
        resultsSection.add(new Label(dollarFormat.format(yInfl) + "%"), 1, 1);
        resultsSection.setVisible(true);
    }

    /**
     * This method is used to display the results of the present value form.
     * @param presentValue The present value
     * @param lostToInflation The money lost to inflation
     */
    public void setToPresVal(double presentValue, double lostToInflation) {
        resultsSection.add(new Label("Present value"), 0, 0);
        resultsSection.add(new Label("$"+dollarFormat.format(presentValue)), 1, 0);
        resultsSection.add(new Label("Lost to inflation"), 0, 1);
        resultsSection.add(new Label("$"+dollarFormat.format(lostToInflation)), 1, 1);
        resultsSection.setVisible(true);
    }

    /**
     * Clear the results section.
     */
    public void clear() {
        resultsSection.getChildren().forEach(n -> n.setVisible(false));
        resultsSection.setVisible(false);
    }
}
