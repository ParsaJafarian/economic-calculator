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

/**
 * This class is used to display data in a tableview with a pagination.
 */
public class Table {

    /**
     * The data to be displayed in the tableView.
     * Initially, it is empty.
     */
    private ObservableList<Row> data = FXCollections.observableArrayList();
    /**
     * The number of rows per page.
     */
    public static final int ROWS_PER_PAGE = 10;
    private final TableView<Row> tableView;
    private final Pagination pagination;

    /**
     * @param tableView  The tableView to be displayed
     * @param pagination The pagination to be displayed
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

        // Set the pagination
        this.pagination = pagination;
        this.updatePagination();
        pagination.setPageFactory(this::createPage);

        this.updateVisibility(false);
    }

    /**
     * This method is used to update the pagination
     * by setting the number of pages to the number of rows divided by the number of rows per page.
     */
    private void updatePagination() {
        pagination.setPageCount((int) Math.ceil((double) data.size() / ROWS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
    }


    /**
     * Set the data in the tableView with the given data.
     * This method also updates the pagination and makes the tableView and pagination visible.
     *
     * @param data the data to be inserted to the tableView
     */
    public void setData(@NotNull ObservableList<Row> data) {
        tableView.getItems().clear();
        //Copies the data so that the table cannot be updated outside of this class
        this.data = FXCollections.observableArrayList(data);
        tableView.getItems().addAll(data);
        this.updatePagination();
        this.updateVisibility(true);
    }

    /**
     * This method is used to create a page(a set of 10 rows from the data) to be displayed in the pagination.
     *
     * @param pageIndex The index of the page to be created
     * @return The page to be displayed
     */
    private @NotNull Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
        tableView.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        return new VBox(tableView);
    }

    /**
     * @param visible Whether the tableView and pagination should be visible or not
     */
    private void updateVisibility(boolean visible) {
        tableView.setVisible(visible);
        pagination.setVisible(visible);
    }

    /**
     * Clear the tableView and the pagination.
     */
    public void clear() {
        tableView.getItems().clear();
        data.clear();
        this.updatePagination();
        this.updateVisibility(false);
    }

    /**
     * Export the data to an excel file using Apache POI.
     * <a href="https://poi.apache.org/">Apache POI</a>
     */
    public void exportToExcel() {

        // If there is no data, show an error message and return
        if (data.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No data to export");
            alert.setContentText("Please calculate the compound interest first");
            alert.showAndWait();
            return;
        }

        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xls"));
        fileChooser.setInitialFileName("data.xls");

        //Show the file chooser
        File file = fileChooser.showSaveDialog(tableView.getScene().getWindow());
        //If the user cancels the file chooser, stop the process
        if (file == null) return;

        //Create the workbook and the sheet
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Compound Calculator");
        HSSFRow firstRow = sheet.createRow(0);

        //Create the first row which are the headers
        firstRow.createCell(0).setCellValue("Time (years)");
        firstRow.createCell(1).setCellValue("Capital ($)");

        //Create the rest of the rows
        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(data.get(i).getTime());
            row.createCell(1).setCellValue(data.get(i).getCapital());
        }

        //Write the workbook to the file
        try {
            workbook.write(file);
            workbook.close();
        } catch (IOException e) {
            //If there is an error, throw a runtime exception
            throw new RuntimeException(e);
        }
    }
}
