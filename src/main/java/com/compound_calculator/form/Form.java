package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Form extends GridPane {
    public ArrayList<Node> fields;

    public Form(){
        fields= new ArrayList<>();
        //making the form have pretty alignment and spacing
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20.0d));
        this.setVgap(10.0d);
        this.setHgap(15.0d);
        this.setBackground(Background.fill(Color.LIGHTGRAY));

    }
    public void clear(){
        //basic function to set all the fields to ""
        for(Node n: this.fields){
            if(n instanceof TextField) ((TextField) n).setText("");
        }
    }
    /**
     * @return true if all text fields in the input section are filled in, false otherwise
     * Used to check if the input is valid before calculating
     */
    public boolean validFields() {
        return fields.stream().allMatch(n -> {
            if (n instanceof TextField textField)
                return !textField.getText().isEmpty();
            return true;
        });
    }
    public ObservableList<Row> getData() {
        return null;
    }
    /**
     * Make all text fields in the input section numeric by
     * looping through all children of the input section.
     * If the child is a text field, add a listener to it
     * that will only allow numeric input.
     */
    protected void makeTextFieldsNumeric() {
        fields.stream().filter(n -> n instanceof TextField).map(n -> (TextField) n).forEach(textField -> textField.textProperty().addListener((observable, oldValue, newValue) -> {

            //Typed characters can only be numeric (\d)
            //Or either "." or "-", to allow negative and decimal values
            if (!newValue.matches("^[\\d-.]+$")) {
                //removes last character if it doesn't match description above
                if(textField.getText().isEmpty()){
                    textField.setText("");
                }else{
                    textField.setText(textField.getText().substring(0, textField.getText().length()-1));
                }

            }
        }));
    }
    @Override
    public String toString(){
        //for the culture!
        return "form!";
    }
}
