package com.compound_calculator;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public class FormUtils {

    private FormUtils(){
        throw new IllegalStateException("Utility class");
    }

    public static void initializeForm(@NotNull GridPane form, @NotNull TextField interestField) {
        makeTextFieldsNumeric(form);
        limitInterestField(interestField);
    }
    /**
     * Make all text fields in the input section numeric by
     * looping through all children of the input section.
     * If the child is a text field, add a listener to it
     * that will only allow numeric input.
     */
    private static void makeTextFieldsNumeric(@NotNull GridPane form) {
        form.getChildren().forEach(n -> {
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

    private static void limitInterestField(@NotNull TextField interestField) {
        interestField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 20)
                interestField.setText(oldValue);
        });
    }

    /**
     * @return true if all text fields in the input section are filled in, false otherwise
     * Used to check if the input is valid before calculating
     */
    public static boolean validFields(@NotNull GridPane form) {
        return form.getChildren().stream().allMatch(n -> {
            if (n instanceof TextField textField)
                return !textField.getText().isEmpty();
            return true;
        });
    }
}
