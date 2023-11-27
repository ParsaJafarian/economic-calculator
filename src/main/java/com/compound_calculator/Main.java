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
        try {
            // Create a Robot object
            Robot robot = new Robot();

            // Get the default toolkit
            Toolkit toolkit = Toolkit.getDefaultToolkit();

            // Get the screen size
            Rectangle screenSize = new Rectangle(toolkit.getScreenSize());

            // Capture the screen
            BufferedImage screenshot = robot.createScreenCapture(screenSize);

            // Define the region to capture (you can adjust these values)
            int x = 100;
            int y = 100;
            int width = 500;
            int height = 300;

            // Create a rectangle representing the region to capture
            Rectangle captureRect = new Rectangle(x, y, width, height);

            // Crop the screenshot to the specified region
            BufferedImage croppedImage = screenshot.getSubimage(captureRect.x, captureRect.y, captureRect.width, captureRect.height);

            // Save the cropped image to a file
            File outputfile = new File("screenshot.png");
            ImageIO.write(croppedImage, "png", outputfile);

            System.out.println("Screenshot saved to: " + outputfile.getAbsolutePath());

        } catch (AWTException | IOException e) {
            throw new RuntimeException("Failed to capture screenshot!", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}