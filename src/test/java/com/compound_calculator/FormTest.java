package com.compound_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormTest {

    //getData from Form.getData() but without JavaFx elements
    private ObservableList<Row> getCompoundData(double initInv, double yearlyAddition, double interest, int years, String frequency) {

        //Get the frequency from the combo box
        final int freq = switch (frequency) {
            case "Monthly" -> 12;
            case "Quarterly" -> 4;
            case "Semi-annually" -> 2;
            case "Yearly" -> 1;
            default -> 0;
        };

        ObservableList<Row> data = FXCollections.observableArrayList();
        data.add(new Row(0, initInv));

        //Loop through the years and calculate the compound interest
        //capital = lastCapital*(1 + i/n)^n + yearlyAddition
        for (int i = 1; i <= years; i++) {
            double lastCapital = data.get(i - 1).getCapital();
            double capital = lastCapital * Math.pow(1 + interest / freq, freq) + yearlyAddition;
            data.add(new Row(i, capital));
        }

        return data;
    }

    @Test
    void getDataTest1() {
        ObservableList<Row> data = getCompoundData(1000, 100, 0.05, 10, "Yearly");

        assert data.size() == 11;
        assert data.get(0).getTime() == 0;
        assert data.get(0).getCapital() == 1000;

        final double finalCapital = data.get(data.size() - 1).getCapital();
        final double expectedFinalCapital = 2886.68;

        // The final capital should be within 1 dollar of the expected final capital
        assertTrue(Math.abs(finalCapital - expectedFinalCapital) < 1);
    }

    @Test
    void getDataTest2() {
        ObservableList<Row> data = getCompoundData(1000, 100, 0.05, 10, "Monthly");

        assert data.size() == 11;
        assert data.get(0).getTime() == 0;
        assert data.get(0).getCapital() == 1000;

        final double finalCapital = data.get(data.size() - 1).getCapital();
        final double expectedFinalCapital = 2911.64;

        // The final capital should be within 1 dollar of the expected final capital
        assertTrue(Math.abs(finalCapital - expectedFinalCapital) < 1);
    }
}
