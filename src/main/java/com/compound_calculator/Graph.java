package com.compound_calculator;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Graph {

    public static LineChart<Number, Number> getLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time (years)");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Capital ($)");
        return new LineChart<>(xAxis, yAxis);
    }

    public static LineChart<Number, Number> getLineChart(ObservableList<Row> data, String title) {
        // Create the x and y axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time (years)");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Capital ($)");

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);

        // Create a data series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(title);

        //If no data is provided, returns a completely virgin line Chart
        if (data.isEmpty()) return lineChart;
        // Add data to the series
        for (Row row : data) {
            series.getData().add(new XYChart.Data<>(row.getTime(), row.getCapital()));
        }

        // Add the series to the chart
        lineChart.getData().add(series);
        return lineChart;
    }
}
