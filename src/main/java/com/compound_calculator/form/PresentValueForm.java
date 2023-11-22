package com.compound_calculator.form;

import com.compound_calculator.form.Form;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PresentValueForm extends Form {
    public PresentValueForm(){

        this.getChildren().add(new Button("Hello there the angel"));
    }
}
