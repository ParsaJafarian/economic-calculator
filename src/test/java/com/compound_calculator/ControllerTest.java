package com.compound_calculator;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ControllerTest {

    @Test
    void initialize() throws IOException {
        Main main = new Main();
        main.start(new Stage());
    }
}