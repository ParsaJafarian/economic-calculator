package com.compound_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Main extends Application {
    /**
     * @param stage The stage to be displayed
     * @throws IOException If the FXML file cannot be loaded
     */
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("index.fxml"));
        Scene scene = new Scene(loader.load(), 600, 700);
        stage.setTitle("Compound Interest Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}