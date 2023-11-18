package com.compound_calculator;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import java.util.ArrayList;

public class Graph{
    public static LineChart<Number, Number> getLineChart(ObservableList<Row> data){
        // Create the x and y axes
        NumberAxis xAxis = new NumberAxis();

        NumberAxis yAxis = new NumberAxis();

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Line Chart Example");

        // Create a data series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        //If no data is provided, returns a completely virgin line Chart
        if(data.isEmpty()) return lineChart;
        // Add data to the series
        for(Row row: data){
            series.getData().add(new XYChart.Data<>(row.getTime(), row.getCapital()));
        }

        // Add the series to the chart
        lineChart.getData().add(series);
        return lineChart;
    }


}
