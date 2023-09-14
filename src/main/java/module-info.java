module com.compound_calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.compound_calculator to javafx.fxml;
    exports com.compound_calculator;
}