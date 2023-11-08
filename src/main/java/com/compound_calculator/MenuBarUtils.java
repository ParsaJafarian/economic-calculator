package com.compound_calculator;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

public class MenuBarUtil {

    private MenuBarUtil(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param fileMenu The file menu
     * @param helpMenu The help menu
     * @param tableController The table controller
     * Initialize the menu bar by adding the menu items.
     * Two menu items are added to the file menu: Export to Excel and Quit
     * One menu item is added to the help menu: About
     */
    public static void initialize(@NotNull Menu fileMenu, @NotNull Menu helpMenu, TableController tableController) {
        MenuItem export = new MenuItem("Export to Excel");
        MenuItem quit = new MenuItem("Quit");
        fileMenu.getItems().addAll(export, quit);

        MenuItem about = new MenuItem("About");
        helpMenu.getItems().add(about);


        export.setOnAction(e -> tableController.exportToExcel());
        quit.setOnAction(e -> System.exit(0));
        about.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Compound Interest Calculator");
            alert.setContentText("Created by Parsa Jafarian & Mark Rudko");
            alert.showAndWait();
        });
    }
}
