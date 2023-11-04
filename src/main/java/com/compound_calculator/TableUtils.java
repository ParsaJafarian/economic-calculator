package com.compound_calculator;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class TableUtils {

    private TableUtils(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Initialize the table by adding the columns.
     * Two columns are added: Time (years) and Capital ($)
     */
    public static void initializeTable(@NotNull TableView<Row> table) {
        // Make the columns
        TableColumn<Row, Integer> timeCol = new TableColumn<>("Time (years)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Row, Double> capitalCol = new TableColumn<>("Capital ($)");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        // Add the columns to the table
        table.getColumns().add(timeCol);
        table.getColumns().add(capitalCol);
    }

    /**
     * Set the data in the table to the given data.
     * This method is called when the calculate button is pressed.
     * @param data the data to be displayed in the table. It is an array of Row objects that contain the time and capital
     */
    public static void setTabularData(@NotNull TableView<Row> table, @NotNull Row @NotNull [] data) {
        table.getItems().clear();
        for (Row row : data)
            table.getItems().add(row);
    }


    public static void exportToExcel(@NotNull TableView<Row> table)  {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Compound Calculator");
        HSSFRow firstRow = sheet.createRow(0);

        firstRow.createCell(0).setCellValue("Time (years)");
        firstRow.createCell(1).setCellValue("Capital ($)");

        for(int i = 0; i < table.getItems().size(); i++){
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(table.getItems().get(i).getTime());
            row.createCell(1).setCellValue(table.getItems().get(i).getCapital());
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls"));
        fileChooser.setInitialFileName("data.xls");

        File file = fileChooser.showSaveDialog(table.getScene().getWindow());

        try{
            workbook.write(file);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
