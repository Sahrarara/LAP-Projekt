package com.lap.lapproject.controller;

import com.lap.lapproject.repos.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lap.lapproject.utility.QuickAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import static com.lap.lapproject.controller.BaseController.model;

public class ChangePasswordController extends Repository {
    @FXML
    private PasswordField currentPasswordTF;
    @FXML
    private PasswordField newPasswordTF;
    @FXML
    private PasswordField repeatNewPasswordTF;


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {
        if (checkFieldsEmpty() && checkOldPasswordIsNotNewPassword() && checkNewRepeatPassword() && this.checkIfPasswordIsInDatabase()){
            //TODO: Hier update Passwort funktion für jetzigen User einfügen. Achtung: Passwort muss gehasht werden
        }
    }

    private boolean checkIfPasswordIsInDatabase() {
        String var10000 = model.getLoggedInUser().getUsername();
        String SELECT_PASSWORD_FROM_USERNAME = "SELECT * FROM users WHERE username = '" + var10000 + "' AND  password = '" + this.currentPasswordTF.getText() + "'";
        Connection connection = connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_PASSWORD_FROM_USERNAME);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                if (resultSet.getString("password").equals(this.currentPasswordTF.getText())) {
                    System.out.println("old password is correct");
                    return true;
                }
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        System.out.println("bullshit");
        return false;
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
        if (currentPasswordTF.getText().equals(repeatNewPasswordTF.getText())){
            QuickAlert.showError("Das neue Passwort darf nicht das alte Passwort sein");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNewRepeatPassword(){       //method checks that you typed in your new password correct twice
        if (!newPasswordTF.getText().equals(repeatNewPasswordTF.getText())){
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
