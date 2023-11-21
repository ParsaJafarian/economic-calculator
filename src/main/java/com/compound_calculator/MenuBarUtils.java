package com.compound_calculator;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import org.jetbrains.annotations.NotNull;

public class MenuBarUtils {

    private MenuBarUtils(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param menuBar The menu bar to be initialized
     * @param table  The table to be exported to Excel
     */
    public static void initializeMenuBar(@NotNull MenuBar menuBar, Table table) {
        Menu fileMenu = new Menu("_File");
        MenuItem export = new MenuItem("Export to Excel");
        export.setAccelerator(KeyCombination.valueOf("Ctrl+E"));
        MenuItem quit = new MenuItem("Quit");
        quit.setAccelerator(KeyCombination.valueOf("Ctrl+Q"));
        fileMenu.getItems().addAll(export, quit);

        Menu helpMenu = new Menu("_Help");
        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);

        export.setOnAction(e -> table.exportToExcel());
        quit.setOnAction(e -> System.exit(0));
        about.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Compound Interest Calculator");
            alert.setContentText("Created by Mark Rudko");
            alert.showAndWait();
        });

        menuBar.getMenus().addAll(fileMenu, helpMenu);
    }
}
