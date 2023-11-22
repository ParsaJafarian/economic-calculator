module com.compound_calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires org.apache.poi.poi;


    opens com.compound_calculator to javafx.fxml;
    exports com.compound_calculator;
    exports com.compound_calculator.forms;
    opens com.compound_calculator.forms to javafx.fxml;
    exports com.compound_calculator.utils;
    opens com.compound_calculator.utils to javafx.fxml;
}