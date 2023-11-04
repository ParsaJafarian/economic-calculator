package com.compound_calculator;

public class Row {
    private final int time;
    private final double capital;
    public Row(int time, double capital) {
        this.time = time;
        this.capital = capital;
    }

    public int getTime() {
        return time;
    }

    public double getCapital() {
        String capital = String.format("%.2f", this.capital);
        return Double.parseDouble(capital);
    }
}
