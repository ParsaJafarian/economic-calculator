package com.compound_calculator;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public class InputSectionUtils {

    private InputSectionUtils(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * Make all text fields in the input section numeric by
     * looping through all children of the input section.
     * If the child is a text field, add a listener to it
     * that will only allow numeric input.
     */
    public static void makeTextFieldsNumeric(@NotNull GridPane inputSection) {
        inputSection.getChildren().forEach(n -> {
            if (n instanceof TextField textField) {
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    //If newly typed string is not numeric, replace it with an empty string
                    //This is done to avoid having to check if the string is numeric later on
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("\\D", ""));
                    }
                });
            }
        });
    }

    /**
     * @return true if all text fields in the input section are filled in, false otherwise
     * Used to check if the input is valid before calculating
     */
    public static boolean validFields(@NotNull GridPane inputSection) {
        return inputSection.getChildren().stream().allMatch(n -> {
            if (n instanceof TextField textField)
                return !textField.getText().isEmpty();
            return true;
        });
    }
}
