package com.compound_calculator.form;
import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class PresentValueForm extends Form {
    private static Label paymentAmountLbl, interestRateLbl, nbYearsLbl, paymentIntervalLbl;
    private static TextField paymentAmountField, interestRateField, nbYearsField;
    private static ComboBox<String> paymentIntervalBox;

    public PresentValueForm(){
        super();
        paymentAmountLbl= new Label("Payment amount ($)");
        paymentAmountField= new TextField();
        interestRateLbl= new Label("Interest rate (%)");
        interestRateField= new TextField();
        nbYearsLbl= new Label("Number of years");
        nbYearsField= new TextField();
        paymentIntervalLbl= new Label("Payment interval");
        paymentIntervalBox= new ComboBox<>();
        paymentIntervalBox.getItems().addAll("Yearly", "Biannually", "Quarterly", "Monthly");
        paymentIntervalBox.getSelectionModel().selectFirst();

        this.add(paymentAmountLbl, 0, 0);
        this.add(paymentAmountField, 1, 0);
        this.add(interestRateLbl, 0, 1);
        this.add(interestRateField, 1, 1);
        this.add(nbYearsLbl, 0, 2);
        this.add(nbYearsField, 1, 2);
        this.add(paymentIntervalLbl, 0, 3);
        this.add(paymentIntervalBox, 1, 3);

        makeTextFieldsNumeric();
    }
    @Override
    public void clear(){
        paymentIntervalBox.getSelectionModel().selectFirst();
        fields.forEach(n -> {
            if (n instanceof TextField textField)
                textField.setText("");
        });
    }
    @Override
    public boolean validFields(){
        return !paymentAmountField.getText().isEmpty() && !interestRateField.getText().isEmpty() && !nbYearsField.getText().isEmpty();
    }
    @Override
    public ObservableList<Row> getData(){
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
        final int interval = switch (paymentIntervalBox.getValue()) {
            case "Monthly" -> 12;
            case "Quarterly" -> 4;
            case "Biannually" -> 2;
            case "Yearly" -> 1;
            default -> 0;
        };
        int nbYears= (int)Double.parseDouble(nbYearsField.getText());
        double annuityPayment = Double.parseDouble(paymentAmountField.getText());
        double yieldToMaturity = Double.parseDouble(interestRateField.getText());

        //Initialize the data array (note: the size is years + 1 because the first row is the initial investment)
        ObservableList<Row> data = FXCollections.observableArrayList();
        //Set the first row to the initial investment
        data.add(new Row(0, 0));
        computePresentValueAnnuity(data, nbYears, annuityPayment, yieldToMaturity);

        return data;
    }
    private double computePresentValueAnnuity(ObservableList<Row> data, int n, double pMT, double r){
        /*
            this is the formula:

            P= pMT/(1+r)^n
            where:
            P= present value of annuity
            pMT= dollar amount each annuity payment
            r= discount/interest rate
            n= number of periods in which the payments will be made

         */
        for(int i=1; i<= n; i++) {

            //double presentValue = pMT * (1- (1/   Math.pow((1+r),i))/r);
            double presentValue= pMT / Math.pow(1+r/100.0f, i);
            data.add(new Row(i, presentValue));
        }
        return 0;
    }
    @Override
    public String toString(){
        return "presentValueForm!";
    }

}
