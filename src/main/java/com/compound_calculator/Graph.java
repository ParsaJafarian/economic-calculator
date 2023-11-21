package com.compound_calculator;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Graph {

   public static LineChart<Number, Number> getLineChart(){
       NumberAxis xAxis = new NumberAxis();

       NumberAxis yAxis = new NumberAxis();
       return new LineChart<>(xAxis, yAxis);
   }
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

    public static void exportToExcel(LineChart<Number, Number> chart){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls"));
        fileChooser.setInitialFileName("chart.xls");

        File file = fileChooser.showSaveDialog(Main.scene.getWindow());
        if (file == null) return;

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chart Data");
        int rowIndex = 0;

        for (XYChart.Series<Number, Number> series : chart.getData()) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(series.getName());

            int cellIndex = 1;
            for (XYChart.Data<Number, Number> data : series.getData()) {
                Cell cell = row.createCell(cellIndex++);
                cell.setCellValue(data.getYValue().doubleValue());
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(file.getAbsoluteFile())) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
