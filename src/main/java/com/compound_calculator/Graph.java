package com.compound_calculator;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;

public class Graph extends AreaChart<Double, Double>{

    public Graph(){

        //Defining the X axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time (years)");
        //Defining the y Axis
        NumberAxis yAxis = new NumberAxis(0, 15, 2.5);
        yAxis.setLabel("Capital ($)");
        super(xAxis, yAxis);
        System.out.println("Hello there, the angel from my nightmare.");

        //Changes ----add changes to stage ------> commit them -----> push them to github
    }

    public void setData(double[] data) {

    }
}
