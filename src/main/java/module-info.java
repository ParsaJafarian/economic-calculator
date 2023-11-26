module com.compound_calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires org.apache.poi.poi;
    requires java.desktop;


    opens com.compound_calculator to javafx.fxml;
    exports com.compound_calculator;
    exports com.compound_calculator.form;
    opens com.compound_calculator.form to javafx.fxml;
}