package com.compound_calculator.util;

import com.compound_calculator.Main;
import com.compound_calculator.Table;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

/**
 * Utility class for the menu bar.
 * It initializes the menu bar and adds the event handlers to the menu items.
 * Moreover, it interacts with the table to export the data to Excel.
 * It also adds the event handler to the screenshot menu item.
 */
public class MenuBarUtils {

    /**
     * Cannot be instantiated
     */
    private MenuBarUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param menuBar The menu bar to be initialized
     * @param table   The table to be exported to Excel
     */
    public static void initializeMenuBar(@NotNull MenuBar menuBar, Table table) {
        // Create the menus
        Menu fileMenu = new Menu("_File");
        Menu helpMenu = new Menu("_Help");

        // Create the menu items for the file menu
        MenuItem export = new MenuItem("Export to Excel");
        MenuItem screenShot = new MenuItem("Export screenshot");
        MenuItem quit = new MenuItem("Quit");

        fileMenu.getItems().addAll(export, screenShot, quit);

        // Set the accelerators
        screenShot.setAccelerator(KeyCombination.valueOf("Ctrl+S"));
        export.setAccelerator(KeyCombination.valueOf("Ctrl+E"));
        quit.setAccelerator(KeyCombination.valueOf("Ctrl+Q"));

        // Create the menu items for the help menu
        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);

        // Add the event handlers
        export.setOnAction(e -> table.exportToExcel());
        screenShot.setOnAction(e -> {
            try {
                Main.captureScreenshot();
            } catch (AWTException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        quit.setOnAction(e -> System.exit(0));
        about.setOnAction(e -> {
            //Display an alert with the information about the program
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Compound Interest Calculator");
            alert.setContentText("Created by Parsa Jafarian & Mark Rudko.");
            alert.showAndWait();
        });

        menuBar.getMenus().addAll(fileMenu, helpMenu);
    }
}
