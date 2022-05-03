package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController {
    @FXML
    private PasswordField currentPasswordTF;
    @FXML
    private PasswordField newPasswordTF;
    @FXML
    private PasswordField repeatNewPasswordTF;


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {
        if (checkFieldsEmpty() && checkOldPasswordIsNotNewPassword() && checkNewRepeatPassword()){ //TODO: Noch eine boolean Funktion die checkt ob das aktuelle Passwort momentan in der Datenbank ist
            //TODO: Hier update Passwort funktion für jetzigen User einfügen. Achtung: Passwort muss gehasht werden
        }
    }

    private boolean checkFieldsEmpty(){     //method checks if all Password Fields are filled
        if (currentPasswordTF.getText().isBlank() || newPasswordTF.getText().isBlank() || repeatNewPasswordTF.getText().isBlank()){
            QuickAlert.showError("Bitte alle Felder ausfüllen");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkOldPasswordIsNotNewPassword(){     //method checks if the old password isn't the new password
        if (currentPasswordTF.getText() == newPasswordTF.getText()){
            QuickAlert.showError("Das neue Passwort darf nicht das alte Passwort sein");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNewRepeatPassword(){       //method checks that you typed in your new password correct twice
        if (newPasswordTF.getText() != repeatNewPasswordTF.getText()){
            QuickAlert.showError("Bitte neues Passwort nochmal überprüfen");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    private Stage getCurrentStage(){
        return (Stage) currentPasswordTF.getScene().getWindow();
    }

}
