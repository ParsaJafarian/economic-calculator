module com.compound_calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires org.apache.poi.poi;


    opens com.compound_calculator to javafx.fxml;
    exports com.compound_calculator;
}