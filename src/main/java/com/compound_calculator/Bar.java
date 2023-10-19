package com.compound_calculator;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class Bar extends MenuBar {
        public Bar() {
            super();
            this.getMenus().add(new Menu("_File"));
            this.getMenus().add(new Menu("_Edit"));
        }
}
