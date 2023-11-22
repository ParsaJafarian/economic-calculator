package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InflationForm extends Form{
    private Label currentCPILbl, previousCPILbl;
    private TextField currentField, previousField;
    public InflationForm(){
        currentCPILbl= new Label("Current consumer price index (CPI)");
        previousCPILbl = new Label("Previous CPI");
        currentField= new TextField();
        currentField.setOnAction(e->{
            System.out.println(currentField.getText());
        });
        previousField= new TextField();

        this.add(currentCPILbl, 0, 0);
        this.add(currentField, 1, 0);
        this.add(previousCPILbl, 0, 1);
        this.add(previousField, 1, 1);

        fields.add(currentField);
        fields.add(previousField);
    }
    @Override
    public ObservableList<Row> getData(){
        //If the input is invalid, display an error message and stop the calculation process
//        if (!this.validFields()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Invalid input");
//            alert.setContentText("Please fill in all fields");
//            alert.showAndWait();
//            return null;
//        }
        double currentCPI= Double.parseDouble(currentField.getText());
        double previousCPI= Double.parseDouble(previousField.getText());
        //Initialize the data array
        ObservableList<Row> data = FXCollections.observableArrayList();
        //Set the first row to the previousCPI, the second, to the current.
        data.add(new Row(0, previousCPI));
        data.add(new Row(1, currentCPI));
        return data;

    }
    @Override
    public boolean validFields(){
        for(Node n: this.fields){
            System.out.println(n);
//            if(n instanceof TextField){
//
//                TextField tf= (TextField) n;
//                System.out.println("["+tf.getText()+"]");
////                return !tf.getText().equals("");
//            }
        }
        return true;
    }

    private double computeInflationRate(double currentCPI, double previousCPI){
        return (currentCPI-previousCPI)/previousCPI *100.0f;
    }
    private double computeYearlyInflationRate(double currentCPI, double previousCPI, double currentYear, double previousYear){
        double inflationRate= computeInflationRate(currentCPI, previousCPI);
        double deltaYears= currentYear-previousYear;
        return inflationRate / deltaYears;
    }
    @Override
    public String toString(){
        return "inflationForm!";
    }
}
