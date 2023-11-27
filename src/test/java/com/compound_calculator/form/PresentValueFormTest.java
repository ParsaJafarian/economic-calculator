package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PresentValueFormTest {
    private double presentValue;
    private double lostToInflation;


    /**
     * Computes the present value of an annuity
     * It also computes the money lost to inflation
     * Compared to
     * <a href="https://www.calculators.org/retirement/annuity.php">
     * Retirement annuity calculator
     * </a>
     *
     * @param n        The number of years
     * @param pMT      The payment amount
     * @param rPercent The interest rate in percent
     * @return The data to be displayed in the tableView
     */
    private ObservableList<Row> computePresentValueAnnuity(int n, double pMT, double rPercent) {
        ObservableList<Row> data = FXCollections.observableArrayList();

        double rDecimal = rPercent / 100.0d;
        //total without inflation is what the money would be worth in a world where inflation is 0.0%
        double totalWithOutInflation = pMT * n ;

        double totalValue = 0;
        data.add(new Row(0, pMT));
        for (int i = 1; i <= n; i ++) {
            double presentValue = pMT / Math.pow(1 + rDecimal, i);
            totalValue += presentValue;
            data.add(new Row(i, presentValue));
        }
        this.presentValue = totalValue;
        //money lost to inflation is the difference between the theoretical capital from the world with 0 inflation and the real world
        //in which the inflation is given in the form
        this.lostToInflation = totalWithOutInflation - totalValue;
        return data;
    }

    @Test
    void computePresentValueAnnuityTest1() {
        final ObservableList<Row> data = computePresentValueAnnuity(15, 25000, 2);
        final double expectedPV = 321231.59;
        assert (data.size() == 16);
        //Assert that the actual value is equal to the expected value within a margin of error of 1$
        assertEquals(expectedPV, this.presentValue, 1);
    }

    @Test
    void computePresentValueAnnuityTest2() {
        final ObservableList<Row> data = computePresentValueAnnuity(20,10000,5);
        final double expectedPV = 124622.10;
        assertEquals(21, data.size(), "The size of the data is not equal to number of years");
        //Assert that the actual value is equal to the expected value within a margin of error of 1$
        assertEquals(expectedPV, this.presentValue, 1);
    }
}
