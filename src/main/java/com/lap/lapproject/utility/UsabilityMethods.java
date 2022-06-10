package com.lap.lapproject.utility;

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
    public static boolean isNumber(String input) {
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isPLZ4(String plz) {
        Pattern pattern = Pattern.compile("^\\d{4}$");
        Matcher matcher = pattern.matcher(plz);
        return matcher.find();
    }



//    public static void changeListener(TextField searchBarTextField, ImageView searchBarIcon){
//        searchBarTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
//            if (!newValue.isBlank()){
//                searchBarIcon.setVisible(false);
//            } else {
//                searchBarIcon.setVisible(true);
//            }
//        }));
//    }
}
