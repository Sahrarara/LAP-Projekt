package com.lap.lapproject.controller;

import javafx.scene.control.Alert;

public class QuickAlert {
    public static void showError(String message){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(message);
        a.show();
    }
}
