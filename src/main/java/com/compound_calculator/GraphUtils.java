package com.compound_calculator;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GraphUtils {

    /**
     * this function allows to remove the graph from the container (VBox)
     * this is done when, for example, new data is entered, or when the user clears the form
     * @param graphContainer the container in which the graph is displayed
     */
    public static void clear(@NotNull VBox graphContainer) {
        //Creates and adds new Line Chart with chosen data to appropriate VBox container named "graphContainer"
        if (!graphContainer.getChildren().isEmpty()) {
            //If a graph was already generated, and the user wishes to generate a new one,
            //everything is removed form graphContainer to allow a new graph to be added.
            //Since there can only be one chart at a time, we only need to remove element 0 form the container.
            //We set it to be invisible first, because otherwise the graphics don't update, and it stays on the screen
            //despite having been deleted.
            Node n = graphContainer.getChildren().get(0);
            n.setVisible(false);
            graphContainer.getChildren().remove(n);
        }
    }

    /**
     * @return virgin LineChart
     */
    @Contract(" -> new")
    public static @NotNull LineChart<Number, Number> getLineChart() {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time (years)");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Capital ($)");
        LineChart<Number, Number> lineChart= new LineChart<>(xAxis, yAxis);
        lineChart.setOnMouseClicked(e-> {
            System.out.println("HI");
        });
        return new LineChart<>(xAxis, yAxis);
    }
    /**
     * @param data the data to be used to generate the graph
     * @param title the title of the graph
     * @return LineChart based on the data provided
     */
    public static @NotNull LineChart<Number, Number> getLineChart(@NotNull ObservableList<Row> data, String title) {
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
        lineChart.setOnMouseClicked(e-> {
            System.out.println("HI");
        });
        return lineChart;
    }
}
