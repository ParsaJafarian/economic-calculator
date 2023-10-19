package com.compound_calculator;

public class Row {
    private int time;
    private double capital;
    public Row(int time, double capital) {
        this.time = time;
        this.capital = capital;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }
}
