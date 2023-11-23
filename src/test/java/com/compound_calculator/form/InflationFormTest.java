package com.compound_calculator.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InflationFormTest {

    @Test
    void computeInflationRateTest1() {
        double inflationRate = InflationForm.computeInflationRate(110, 100);
        assertEquals(10, inflationRate, "Inflation rate: " + inflationRate + " Expected inflation rate: 10%");
    }

    @Test
    void computeInflationRateTest2() {
        double inflationRate = InflationForm.computeInflationRate(100, 110);
        assertEquals(-9.090909090909092, inflationRate, "Inflation rate: " + inflationRate + " Expected inflation rate: -9.090909090909092%");
    }

    @Test
    void computeYearlyInflationRateTest1() {
        double yearlyInflationRate = InflationForm.computeYearlyInflationRate(110, 100, 10, 9);
        assertEquals(10, yearlyInflationRate, "Yearly inflation rate: " + yearlyInflationRate + " Expected yearly inflation rate: 10%");
    }

    @Test
    void computeYearlyInflationRateTest2() {
        double yearlyInflationRate = InflationForm.computeYearlyInflationRate(150,100, 10,0);
        assertEquals( 5, yearlyInflationRate, "Yearly inflation rate: " + yearlyInflationRate + " Expected yearly inflation rate: 5%");
    }
}
