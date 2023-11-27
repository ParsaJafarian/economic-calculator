package com.compound_calculator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

/**
 * This class is used to represent a row in the table.
 * It contains the time and capital of the row.
 */
public class Row {
    // The time and capital are stored as ObservableValue so that they can be displayed in the table
    private final ObservableValue<Integer> time;
    private final ObservableValue<Double> capital;

    /**
     * @param time    The time (in years)
     * @param capital The capital (in dollars)
     */
    public Row(int time, double capital) {
        this.time = new SimpleObjectProperty<>(time);
        this.capital = new SimpleObjectProperty<>(capital);
    }

    /**
     * @return The time (in years)
     */
    public int getTime() {
        return time.getValue();
    }

    /**
     * @return The capital (in dollars) after reformatting it to 2 decimal places
     */
    public double getCapital() {
        String capital = String.format("%.2f", this.capital.getValue());
        return Double.parseDouble(capital);
    }
}
