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
     *
     */
    public static Scene scene;
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        /*
         * pulls the file index.fxml to set the scene
         */
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("index.fxml"));
        scene = new Scene(loader.load(), 1000, 700);
        stage.setTitle("Economic Functions Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}