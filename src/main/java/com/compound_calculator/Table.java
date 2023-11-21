package com.compound_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

public class Table {

    private ObservableList<Row> data = FXCollections.observableArrayList();
    public static final int ROWS_PER_PAGE = 10;
    private final TableView<Row> tableView;
    private final Pagination pagination;

    /**
     * Initialize the tableView by adding the columns.
     * Two columns are added: Time (years) and Capital ($)
     */
    public Table(TableView<Row> tableView, @NotNull Pagination pagination) {
        // Make the columns
        this.tableView = tableView;

        TableColumn<Row, Integer> timeCol = new TableColumn<>("Time (years)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Row, Double> capitalCol = new TableColumn<>("Capital ($)");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        // Add the columns to the tableView
        this.tableView.getColumns().add(timeCol);
        this.tableView.getColumns().add(capitalCol);

        this.pagination = pagination;
        this.updatePagination();
        pagination.setPageFactory(this::createPage);

        this.updateVisibility(false);
    }

    private void updatePagination() {
        pagination.setPageCount((int) Math.ceil((double) data.size() / ROWS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
    }


    /**
     * Set the data in the tableView to the given data.
     * This method is called when the calculate button is pressed.
     *
     * @param data the data to be displayed in the tableView. It is an array of Row objects that contain the time and capital
     */
    public void setData(@NotNull ObservableList<Row> data) {
        tableView.getItems().clear();
        //Copies the data so that the table cannot be updated outside of this class
        this.data = FXCollections.observableArrayList(data);
        tableView.getItems().addAll(data);
        this.updatePagination();
        this.updateVisibility(true);
    }

    private @NotNull Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
        tableView.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        return new VBox(tableView);
    }

    private void updateVisibility(boolean visible) {
        tableView.setVisible(visible);
        pagination.setVisible(visible);
    }

    public void clear() {
        tableView.getItems().clear();
        data.clear();
        this.updatePagination();
        this.updateVisibility(false);
    }

    public void exportToExcel() {

        if (data.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No data to export");
            alert.setContentText("Please calculate the compound interest first");
            alert.showAndWait();
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls"));
        fileChooser.setInitialFileName("data.xls");

        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        if (file == null) return;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Compound Calculator");
        HSSFRow firstRow = sheet.createRow(0);

        firstRow.createCell(0).setCellValue("Time (years)");
        firstRow.createCell(1).setCellValue("Capital ($)");

        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(data.get(i).getTime());
            row.createCell(1).setCellValue(data.get(i).getCapital());
        }

        try {
            workbook.write(file);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
