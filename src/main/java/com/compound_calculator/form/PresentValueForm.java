package com.compound_calculator.form;
import com.compound_calculator.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class PresentValueForm extends Form {
    private final TextField paymentAmountField, interestRateField, nbYearsField;
    private final ComboBox<String> paymentIntervalBox;
    private double presentValue, lostToInflation;

    public PresentValueForm(){
        super();
        Label paymentAmountLbl= new Label("Payment amount ($)");
        paymentAmountField= new TextField();
        Label interestRateLbl= new Label("Inflation rate (%)");
        interestRateField= new TextField();
        Label nbYearsLbl= new Label("Number of years");
        nbYearsField= new TextField();
        Label paymentIntervalLbl= new Label("Payment interval");
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
        fields.add(paymentAmountField);
        fields.add(interestRateField);
        fields.add(nbYearsField);
        makeTextFieldsNumeric();
    }

    public void clear(){
        paymentIntervalBox.getSelectionModel().selectFirst();
        paymentAmountField.setText("");
        interestRateField.setText("");
        nbYearsField.setText("");
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
        final int freq = switch (paymentIntervalBox.getValue()) {
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

        computePresentValueAnnuity(data, nbYears, annuityPayment, yieldToMaturity, Math.pow(freq, -1));

        return data;
    }
    private double computePresentValueAnnuity(ObservableList<Row> data, int n, double pMT, double rPercent, double interval){
        /*
            this is the formula:

            P= SUM(pMT/(1+r)^n)
            where:
            P= present value of annuity
            pMT= dollar amount each annuity payment
            r= discount/interest rate (in decimal form)
            n= number of periods in which the payments will be made

         */
        double rDecimal= rPercent/100.0d;
        double totalWithOutInflation= pMT*n*(1.0/interval);

        double totalValue= 0;
        for(float i=0; i<= n; i+=interval) {
            double presentValue= pMT / Math.pow(1+rDecimal, i);
            totalValue+= presentValue;
            data.add(new Row((int)i, presentValue));
        }
        this.presentValue= totalValue;
        this.lostToInflation= totalWithOutInflation-totalValue;
        return totalValue;
    }
    public double getPresentValue(){
        return this.presentValue;
    }

    public double getLostToInflation() {
        return lostToInflation;
    }

    public static void displayInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Present Value");
        alert.setHeaderText("How it works");
        alert.setContentText("Present value is an economic concept that reflects the current worth of a sum of" +
                " money or a series of future cash flows, taking into account the time value of money." +
                " It recognizes that a given amount of money today is more valuable than the same" +
                " amount in the future due to the potential for earning returns or interest." +
                " To learn more, visit https://www.investopedia.com/terms/p/presentvalue.asp");
        alert.showAndWait();
    }

    @Override
    public String toString(){
        return "presentValueForm!";
    }

}
