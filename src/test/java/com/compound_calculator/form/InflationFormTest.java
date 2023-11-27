package com.compound_calculator.form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Inflation Data is compared to calculations done by hand.
 */
public class InflationFormTest {

    @Test
    void computeInflationRateTest1() {
        double inflationRate = InflationForm.computeInflationRate(110, 100);
        assertEquals(10, inflationRate);
    }

    @Test
    void computeInflationRateTest2() {
        double inflationRate = InflationForm.computeInflationRate(100, 110);
        assertEquals(-9.090909090909092, inflationRate);
    }

    @Test
    void computeYearlyInflationRateTest1() {
        double yearlyInflationRate = InflationForm.computeYearlyInflationRate(110, 100, 10, 9);
        assertEquals(10, yearlyInflationRate);
    }

    @Test
    void computeYearlyInflationRateTest2() {
        double yearlyInflationRate = InflationForm.computeYearlyInflationRate(150,100, 10,0);
        assertEquals( 5, yearlyInflationRate);
    }
}
