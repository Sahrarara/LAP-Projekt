package com.lap.lapproject.utility;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class UsabilityMethods {
    public static void changeListener(TextField searchBarTextField, ImageView searchBarIcon){
        searchBarTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.isBlank()){
                searchBarIcon.setVisible(false);
            } else {
                searchBarIcon.setVisible(true);
            }
        }));
    }
}
