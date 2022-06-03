package com.lap.lapproject.controller;

import com.lap.lapproject.repos.Repository;


import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.PasswordSecurity;
import com.lap.lapproject.utility.QuickAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lap.lapproject.controller.BaseController.model;


public class ChangePasswordController extends Repository {

    private static final Logger logger = LoggerFactory.getLogger(PasswordSecurity.class);

    @FXML
    private PasswordField currentPasswordTF;
    @FXML
    private PasswordField newPasswordTF;
    @FXML
    private PasswordField repeatNewPasswordTF;


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {

        int userID = model.getLoggedInUser().getId();
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

        if (isValidate() && isNewPasswordNotOldPassword() && checkNewRepeatPassword()) {

            String hashedPass = PasswordSecurity.hashPassword(newPasswordTF.getText());
            userRepositoryJDBC.updatePassword(hashedPass, userID);
            model.getLoggedInUser().setUserPassword(hashedPass);

            logger.info("newPlainPass: {}", newPasswordTF.getText());
            logger.info("newHashedPass: {}", hashedPass);

            moveToProfilePage();
        }

    }



    private boolean isValidate() {
        if (currentPasswordTF.getText() != null && !(currentPasswordTF.getText().isBlank())
                && newPasswordTF.getText() != null && !(newPasswordTF.getText().isBlank())) {
            return true;
        }
        QuickAlert.showError("Bitte alle Felder ausfüllen");
        return false;
    }



    private boolean isNewPasswordNotOldPassword() {     //method checks if the old password isn't the new password
        if (!currentPasswordTF.getText().equals(repeatNewPasswordTF.getText())){
            return true;
        } else {
            QuickAlert.showError("Das neue Passwort kann nicht wie alte Passwort sein");
            return false;
        }
    }


    private boolean checkNewRepeatPassword() {       //method checks that you typed in your new password correct twice
        if (newPasswordTF.getText().equals(repeatNewPasswordTF.getText())){
            return true;
        } else {
            QuickAlert.showError("Bitte neues Passwort nochmal überprüfen");
            return false;

        }
    }


    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    private void moveToProfilePage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
        QuickAlert.showError("Passwort erfolgreich geändert.");
    }

    private Stage getCurrentStage(){
        return (Stage) currentPasswordTF.getScene().getWindow();
    }












}
