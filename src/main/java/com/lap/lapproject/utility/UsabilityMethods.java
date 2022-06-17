package com.lap.lapproject.utility;

import com.lap.lapproject.controller.BaseController;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.lap.lapproject.controller.ProfileController.imageFromBytes;

public class UsabilityMethods {

    public static boolean isDDMMYYYYDate(String ddMMYYYY) {
        Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");
        Matcher matcher = pattern.matcher(ddMMYYYY);
        return matcher.find();
    }
    public static boolean isNumber(String input) {
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static boolean isPLZ4(String plz) {
        Pattern pattern = Pattern.compile("^\\d{4}$");
        Matcher matcher = pattern.matcher(plz);
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

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern p = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
        Matcher m = p.matcher(phoneNumber);
        if (m.find() && m.group().equals(phoneNumber)) {
            return true;
        }
        return false;
    }


    @FXML
    public static boolean isEmailValid(String email) {
        Pattern compile = Pattern.compile("[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}");
        Matcher matcher = compile.matcher(email);
        return matcher.matches();
    }


    public static void changeListenerForPhoneNr(TextField textField, Label label) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!UsabilityMethods.isPhoneNumberValid(newValue)) {
                label.setText("Bitte dieses Format verwenden: +43 1234567");
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        }));
    }


    public static void changeListenerForEmail(TextField textField, Label label) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!UsabilityMethods.isEmailValid(newValue)) {
                if (!textField.getText().equals("")) {
                    label.setText("Bsp. 'max@gmail.com'");
                    label.setVisible(true);
                }
            } else {
                label.setVisible(false);
            }
        }));
    }

    public static void changeListenerForNumber(TextField textField, Label label) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!UsabilityMethods.isNumber(newValue)) {
                if (!textField.getText().equals("")) {
                    label.setText("Bitte geben Sie nur Zahlen!");
                    label.setVisible(true);
                }
            } else {
                label.setVisible(false);
            }
        }));
    }

    public static void changeListenerForZip(TextField textField, Label label) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!UsabilityMethods.isPLZ4(newValue)) {
                if (!textField.getText().equals("")) {
                    label.setText("Bitte geben Sie ein 4 Stellige Zahl!");
                    label.setVisible(true);
                }
            } else {
                label.setVisible(false);
            }
        }));
    }

    public static void changeListenerInputText(TextField textField, Label label) {
        textField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.isBlank()){
                label.setVisible(false);
            } else {
                label.setVisible(false);
            }
        }));
    }
    public static void changeListenerDataPicker(DatePicker datePicker, Label label) {
        datePicker.valueProperty().addListener(((observable, oldValue, newValue) -> {
                label.setVisible(false);

        }));
    }

    public static void  addMessage(Label label, String text) {
        label.setText(text);
        label.setVisible(true);
    }

}
