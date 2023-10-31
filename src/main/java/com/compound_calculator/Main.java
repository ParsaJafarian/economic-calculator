package com.compound_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        BorderPane root = new BorderPane();
//
//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(20));
//        VBox vBox = new VBox(20);
//
//        Table table = new Table();
//
//        vBox.getChildren().addAll(new InputSection(table), table);
//        hBox.getChildren().addAll(vBox);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));


<<<<<<< HEAD
        Scene scene = new Scene(loader.load(), 320, 240);
=======
        vBox.getChildren().addAll(new InputSection(table), table);
        hBox.getChildren().addAll(vBox);

        root.setTop(new Bar());
        root.setCenter(hBox);

        //provide ArrayList<Double> to this Graph object

        //Graph graph= new Graph();
        //root.setRight(new );

        Scene scene = new Scene(root, 320, 240);
>>>>>>> 85de1db58eb7e3c96c0bf9f02dbf47abb2ef13c4
        stage.setTitle("Compound Interest Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}