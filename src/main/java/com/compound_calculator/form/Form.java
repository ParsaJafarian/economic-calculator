package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Form extends GridPane {

    protected final ArrayList<Node> fields;


    /**
     * <h1>The Form class</h1>
     * <h2>The form class allows to take input from the user for all the necessary fields.</h2>
     * <h7>For each economic formula included in this project, there is one form class that extends this one.
     * The class provides all the fields required to make the calculations, whether it's the amount of years or the
     * inflation rate, etc. This class is just an abstraction from which the other forms can inherit their important functions and attributes</h7>
     */
    public Form() {
        fields = new ArrayList<>();
        //making the form have pretty alignment and spacing
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20.0d));
        this.setVgap(10.0d);
        this.setHgap(15.0d);
        this.setBackground(Background.fill(Color.LIGHTGRAY));
    }

    /**
     * <h3>Clears all the fields that are TextFields: sets them to ""</h3>
     */
    public void clear() {
        //basic function to set all the fields to ""
        for (Node n : this.fields)
            if (n instanceof TextField textField) textField.setText("");
    }

    /**
     * @return true if all text fields in the input section are filled in, false otherwise
     * Used to check if the input is valid before calculating
     */
    public boolean validFields() {
        return fields.stream().allMatch(n -> {
            if (n instanceof TextField textField) return !textField.getText().isEmpty();
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
                if (textField.getText().isEmpty()) textField.setText("");
                else textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            }
        }));
    }

    /**
     * Limit the input of all text fields in the input section to 1 million.
     */
    protected void limitFields() {
        fields.forEach(n -> {
            if (n instanceof TextField textField) {
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        if (!newValue.isEmpty() && Double.parseDouble(newValue) > 1000000) textField.setText(oldValue);
                    } catch (Exception e) {
                        textField.setText(oldValue);
                    }
                });
            }
        });
    }

    /**
     * Limit the input of a text field to 20%.
     *
     * @param field The text field to be limited to 20%
     */
    protected void limitPercentField(@NotNull TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty() && Double.parseDouble(newValue) > 20) field.setText(oldValue);
            } catch (Exception e) {
                //if the input is not numeric, set the text to the old value
                field.setText(oldValue);
            }
        });
    }

    public void displayInformationAlert() {

    }

    @Override
    public String toString() {
        //for the culture!
        return "form!";
    }
}
