package com.compound_calculator.forms;

import com.compound_calculator.Row;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

abstract class Form {

    protected final GridPane gridPane;

    protected Form(GridPane gridPane) {
        super();
        this.gridPane = gridPane;
    }

    public abstract void clear();
    public abstract boolean validFields();
    public abstract ObservableList<Row> getData();
}
