package com.compound_calculator;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Form extends GridPane {

    //NB: current type
    //0 -> compound
    //1 -> present value
    //2 -> inflation
    public static int currentType= 0;
    public ObservableList<Node> fields;

    public void extractFields(GridPane givenForm){
        this.fields = givenForm.getChildren();
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

}
