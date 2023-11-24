package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class CompoundForm extends Form {
    private Label freqLbl, interestLbl, initInvLbl, yearlyAdditionLbl, yearsLbl, yearsSliderLbl;
    private ComboBox<String> freqBox;
    private TextField interestField, initInvField, yearlyAdditionField;
    private Slider yearsSlider;

    public CompoundForm() {
        super();
        initInvLbl= new Label("Initial investment ($)");
        initInvField= new TextField();

        yearlyAdditionLbl= new Label("Yearly addition ($)");
        yearlyAdditionField= new TextField();
        limitFields();
        interestLbl= new Label("Interest Rate (%)");
        interestField= new TextField();
        limitInterestField();

        freqLbl= new Label("Compound frequency");
        freqBox= new ComboBox<>();
        freqBox.getItems().addAll("Yearly", "Biannually", "Quarterly", "Monthly");
        freqBox.getSelectionModel().selectFirst();

        yearsLbl= new Label("Number of years");

        yearsSlider= new Slider();
        yearsSliderLbl= new Label("0");
        yearsSlider.setMin(1.0d);
        yearsSlider.setMax(50.0d);
        yearsSlider.setOnMouseReleased(e ->{
            yearsSliderLbl.setText((int)yearsSlider.getValue()+"");
        });

        this.add(initInvLbl, 0, 0);
        this.add(initInvField, 1, 0);
        this.add(yearlyAdditionLbl, 0, 1);
        this.add(yearlyAdditionField, 1, 1);
        this.add(interestLbl, 0, 2);
        this.add(interestField, 1, 2);
        this.add(freqLbl, 0, 3);
        this.add(freqBox, 1, 3);
        this.add(yearsLbl, 0, 4);
        this.add(yearsSlider, 1, 4);
        this.add(yearsSliderLbl, 2, 4);

        fields.add(initInvField);
        fields.add(yearlyAdditionField);
        fields.add(interestField);
        fields.add(freqBox);
        fields.add(yearsSlider);
        makeTextFieldsNumeric();

    }

    @Override
    public void clear() {
        yearsSlider.setValue(0);
        freqBox.getSelectionModel().selectFirst();
        fields.forEach(n -> {
            if (n instanceof TextField textField)
                textField.setText("");
        });
    }
    public ObservableList<Row> getData() {
        //If the input is invalid, display an error message and stop the calculation process
        if (!this.validFields()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return null;
        }

        //Get the frequency from the combo box
        final int freq = switch (freqBox.getValue()) {
            case "Monthly" -> 12;
            case "Quarterly" -> 4;
            case "Biannually" -> 2;
            case "Yearly" -> 1;
            default -> 0;
        };

        //Get the input from the text fields
        final double initInv = Double.parseDouble(initInvField.getText());
        final double yearlyAddition = Double.parseDouble(yearlyAdditionField.getText());
        final double interest = Double.parseDouble(interestField.getText()) / 100;
        final int years = (int)yearsSlider.getValue();

        return computeCompoundInterest(initInv, years, interest, freq, yearlyAddition);
    }
    static ObservableList<Row> computeCompoundInterest(double initInv, int years, double interest, int freq, double yearlyAddition){
        //Initialize the data array (note: the size is years + 1 because the first row is the initial investment)
        ObservableList<Row> data = FXCollections.observableArrayList();
        //Set the first row to the initial investment
        data.add(new Row(0, initInv));
        //Loop through the years and calculate the compound interest
        //capital = lastCapital*(1 + i/n)^n + yearlyAddition
        for (int i = 1; i <= years; i++) {
            double lastCapital = data.get(i - 1).getCapital();
            double capital = lastCapital * Math.pow(1 + interest / freq, freq) + yearlyAddition;
            data.add(new Row(i, capital));
        }
        return data;
    }

    public double getYearlyAddition() {
        return Double.parseDouble(yearlyAdditionField.getText());
    }

    private void limitInterestField() {
        interestField.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if (!newValue.isEmpty() && Double.parseDouble(newValue) > 20)
                    interestField.setText(oldValue);
            }catch(Exception e){
                System.out.println(e +"Exception caught:)");
            }

        });
    }
    private void limitFields(){
        initInvField.textProperty().addListener((observable, oldValue, newValue)->{
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 1000000) initInvField.setText(oldValue);
        });
        yearlyAdditionField.textProperty().addListener((observable, oldValue, newValue)->{
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 1000000) yearlyAdditionField.setText(oldValue);
        });

    }

    @Override
    public String toString(){
        return "compoundForm!";
    }
}
