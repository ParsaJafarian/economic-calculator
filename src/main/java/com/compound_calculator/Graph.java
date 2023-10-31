package com.compound_calculator;
import javafx.scene.chart.*;
import java.util.ArrayList;

public class Graph{
    private static CategoryAxis xAxis, yAxis;
    private static BarChart graph;
    private static XYChart.Series data;
    public Graph(ArrayList<Double> chartData){

        xAxis= new CategoryAxis();
        xAxis.setLabel("Time (Y)");
        yAxis= new CategoryAxis();
        yAxis.setLabel("Capital CAD");

        graph= new BarChart(xAxis, yAxis);
        data= new XYChart.Series();
        data.setName("data");
        try {
            passData(chartData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        graph.getData().add(data);

    }
    public void passData(ArrayList<Double> importedData) throws Exception {
        //Format :
        //(x, y, x, y, x, y ...)
        //
        if(importedData.size()%2!=0){
            throw new Exception("Unable to match X and Y values 1:1");
        }
        for(int i=0; i< importedData.size()-1; i++){
            Double xValue= importedData.get(i);
            Double yValue= importedData.get(i+1);

            data.getData().add(new XYChart.Data(xValue, yValue));
        }

    }

}
