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

    //NB: current type
    //0 -> compound
    //1 -> present value
    //2 -> inflation
    public static int currentType= 0;
    public ArrayList<Node> fields;

    public Form(){
        fields= new ArrayList<Node>();
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20.0d));
        this.setVgap(10.0d);
        this.setHgap(15.0d);
        this.setBackground(Background.fill(Color.LIGHTGRAY));

    }
    public static void switchToPresentValueForm(GridPane current){

    }
    public static void switchToCompoundInterestForm(GridPane current){

    }
    public static void switchToInflationForm(GridPane current){

    }

    public void extractFields(GridPane givenForm){
        if(givenForm==null)return;
        this.fields = (ArrayList<Node>) givenForm.getChildren();
    }
    public void clear(){
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
}
