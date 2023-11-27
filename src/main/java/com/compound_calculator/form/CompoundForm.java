package com.compound_calculator.form;

import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class CompoundForm extends Form {
    private final  Label yearsSliderLbl;
    private final ComboBox<String> freqBox;
    private final TextField interestField, initInvField, yearlyAdditionField;
    private final Slider yearsSlider;

    /**
     *
     *
 *      <h1>Compound calculator Form</h1>
 *       <h2>This class extends Form() and allows to specify all the necessary fields that are required to make the 'compound' form</h2>
 *       <h3>These fields include: initial investment, yearly addition, interest rate, compound frequency, and the number of years</h3>
     *
     */
    public CompoundForm() {
        super();
        //initiating all the labels and fields that comprise this form
        Label initInvLbl= new Label("Initial investment ($)");
        initInvField= new TextField();
        Label yearlyAdditionLbl= new Label("Yearly addition ($)");
        yearlyAdditionField= new TextField();
        limitFields();
        Label interestLbl= new Label("Interest Rate (%)");
        interestField= new TextField();
        limitInterestField();

        Label freqLbl= new Label("Compound frequency");
        freqBox= new ComboBox<>();
        freqBox.getItems().addAll("Yearly", "Biannually", "Quarterly", "Monthly");
        freqBox.getSelectionModel().selectFirst();

        Label yearsLbl= new Label("Number of years");

        yearsSlider= new Slider();
        yearsSliderLbl= new Label("0");
        yearsSlider.setMin(1.0d);
        yearsSlider.setMax(50.0d);
        yearsSlider.setOnMouseReleased(e ->{
            yearsSliderLbl.setText((int)yearsSlider.getValue()+"");
        });
        //adding all labels and fields to the GridPane that this form extends
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
        //adding the fields to the fields arrayList to make them numeric
        fields.add(initInvField);
        fields.add(yearlyAdditionField);
        fields.add(interestField);
        fields.add(freqBox);
        fields.add(yearsSlider);
        makeTextFieldsNumeric();

    }

    @Override
    public void clear() {
        //resetting all the textFields to "" and the slider to 0
        //also, setting the frequency selection comboBox to the first value
        //(annually)
        yearsSlider.setValue(0);
        yearsSliderLbl.setText("0");
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

    /**
     * <h1>The purpose of this function is to return the capital after a given amount of years, given the parameters of the interest rate at the bank</h1>
     * @param initInv initial investment
     * @param years number of years
     * @param interest interest rate
     * @param freq compound frequency
     * @param yearlyAddition yearly addition
     * @return Observable list of rows to add to the table
     */
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
        //this function makes sure that the interest is not above 20%
        interestField.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if (!newValue.isEmpty() && Double.parseDouble(newValue) > 20)
                    interestField.setText(oldValue);
            }catch(Exception e){
                System.out.println(e +"Exception caught:)");
            }

        });
    }

    public void displayInformationAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Compound Interest");
        alert.setHeaderText("How it works");
        alert.setContentText("Compound interest involves earning or paying interest on" +
                " both the initial amount and the previously earned interest, resulting" +
                " in the exponential growth of the total amount over time. To learn more," +
                " visit https://en.wikipedia.org/wiki/Compound_interest");
        alert.showAndWait();
    }

    @Override
    public String toString(){
        //making sure to add a 'toString()' to respect convention, because this
        //class will be instantiated
        return "compoundForm!";
    }
}
