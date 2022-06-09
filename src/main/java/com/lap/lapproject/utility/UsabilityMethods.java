package com.lap.lapproject.utility;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsabilityMethods {

    public static boolean isDDMMYYYYDate(String ddMMYYYY) {
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");
        Matcher matcher = pattern.matcher(ddMMYYYY);
        return matcher.find();
    }



    public static void changeListener(TextField searchBar, Button closeIconButton) {
        searchBar.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.isBlank()){
                closeIconButton.setVisible(true);
            } else {
                closeIconButton.setVisible(false);
            }
        }));
    }

}
