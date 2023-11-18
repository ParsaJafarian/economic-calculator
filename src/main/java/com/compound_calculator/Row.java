package com.compound_calculator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class Row {
    private final ObservableValue<Integer> time;
    private final ObservableValue<Double> capital;

    public Row(int time, double capital) {
        this.time = new SimpleObjectProperty<>(time);
        this.capital = new SimpleObjectProperty<>(capital);
    }

    public int getTime() {
        return time.getValue();
    }

    public double getCapital() {
        String capital = String.format("%.2f", this.capital.getValue());
        return Double.parseDouble(capital);
    }
}
