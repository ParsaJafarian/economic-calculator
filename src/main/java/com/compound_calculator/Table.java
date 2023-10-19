package com.compound_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Table extends TableView{

//    private final int itemsPerPage = 5; // Define the number of items per page
//    private ObservableList<String> data = FXCollections.observableArrayList(); // Your data here

    public Table() {
        super();
        TableColumn<Integer,String> timeCol = new TableColumn<>("Time (years)");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Double, String> capitalCol = new TableColumn<>("Capital ($)");
        capitalCol.setCellValueFactory(new PropertyValueFactory<>("capital"));

        this.getColumns().add(timeCol);
        this.getColumns().add(capitalCol);

        this.getItems().add(new Row(0, 0.0));

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        //@TODO: Add pagination
//        Pagination pagination = new Pagination((int) Math.ceil((double) data.size() / itemsPerPage), 0);
//        pagination.setPageFactory(pageIndex -> {
//            this.setItems(getItemsForPage(pageIndex));
//            return new VBox(this);
//        });
//
//        Button nextButton = new Button("Next");
//        nextButton.setOnAction(e -> {
//            int nextPage = pagination.getCurrentPageIndex() + 1;
//            if (nextPage < pagination.getPageCount()) {
//                pagination.setCurrentPageIndex(nextPage);
//            }
//        });

    }

//    private ObservableList<String> getItemsForPage(int pageIndex) {
//        int startIndex = pageIndex * itemsPerPage;
//        int endIndex = Math.min(startIndex + itemsPerPage, data.size());
//        return FXCollections.observableArrayList(data.subList(startIndex, endIndex));
//    }
}
