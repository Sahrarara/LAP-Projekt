package com.lap.lapproject.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Optional;

public class QuickAlert {
    public static void showError(String message){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(message);
        a.show();
    }
    public static void showInfo(String message){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(message);
        a.show();
    }


}
