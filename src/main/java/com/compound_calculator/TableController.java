package com.compound_calculator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class TableController {

    private ObservableList<Row> data = FXCollections.observableArrayList();
    public static final int ROWS_PER_PAGE = 10;
    private final TableView<Row> table ;
    private final Pagination pagination;

    /**
     * Initialize the table by adding the columns.
     * Two columns are added: Time (years) and Capital ($)
     */
    public TableController(TableView<Row> table, @NotNull Pagination pagination){
        // Make the columns
        this.table = table;

        TableColumn<Row, Integer> timeCol = new TableColumn<>("Time (years)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Row, Double> capitalCol = new TableColumn<>("Capital ($)");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        // Add the columns to the table
        this.table.getColumns().add(timeCol);
        this.table.getColumns().add(capitalCol);

        this.pagination = pagination;
        this.updatePagination();
        pagination.setPageFactory(this::createPage);
    }

    private void updatePagination(){
        pagination.setPageCount((int) Math.ceil((double) data.size() / ROWS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
    }


    /**
     * Set the data in the table to the given data.
     * This method is called when the calculate button is pressed.
     * @param data the data to be displayed in the table. It is an array of Row objects that contain the time and capital
     */
    public void setData(@NotNull ObservableList<Row> data) {
        table.getItems().clear();
        this.data = data;
        table.getItems().addAll(data);
        this.updatePagination();
    }

    private @NotNull Node createPage(int pageIndex){
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        return new VBox(table);
    }

    public void exportToExcel()  {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Compound Calculator");
        HSSFRow firstRow = sheet.createRow(0);

        firstRow.createCell(0).setCellValue("Time (years)");
        firstRow.createCell(1).setCellValue("Capital ($)");

        for(int i = 0; i < data.size(); i++){
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(data.get(i).getTime());
            row.createCell(1).setCellValue(data.get(i).getCapital());
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

    public static class Row {
        private final ObservableValue<Integer> time;
        private final ObservableValue<Double> capital;
        public Row(int time, double capital) {
            this.time = new SimpleObjectProperty<>(time);
            this.capital = new SimpleObjectProperty<>(capital);
        }

        public int getTime() {
            return time.getValue();
        }

        public double getCapital() {
            String capital = String.format("%.2f", this.capital.getValue());
            return Double.parseDouble(capital);
        }
    }

}
