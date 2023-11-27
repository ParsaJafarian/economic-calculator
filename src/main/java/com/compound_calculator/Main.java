package com.compound_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static Scene scene;

    /**
     * @param stage The stage to be displayed
     * @throws IOException If the FXML file cannot be loaded
     */
    @Override
    public void start(@NotNull Stage stage) throws IOException {
        //pulls the file index.fxml to set the scene
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("index.fxml"));
        scene = new Scene(loader.load(), 1000, 700);
        stage.setTitle("Economic Functions Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void captureScreenshot() throws AWTException, IOException {
        // Create a Robot
        Robot robot = new Robot();

        // Get the default toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size
        Rectangle screenSize = new Rectangle(toolkit.getScreenSize());

        // Capture the screenshot
        BufferedImage screenshot = robot.createScreenCapture(screenSize);

        // Save the screenshot to a file
        ImageIO.write(screenshot, "png", new File("Screenshot.png"));
    }

    public static void main(String[] args) {
        launch(args);
    }
}