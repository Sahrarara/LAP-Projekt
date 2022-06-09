package com.lap.lapproject.controller;

import com.lap.lapproject.repos.Repository;

import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.PasswordSecurity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

import java.sql.SQLException;

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
    private Label errorPasswordLabel;


    @FXML
    void initialize() {
        assert currentPasswordTF != null : "fx:id=\"currentPasswordTF\" was not injected: check your FXML file 'changepassword-view.fxml'.";
        assert newPasswordTF != null : "fx:id=\"newPasswordTF\" was not injected: check your FXML file 'changepassword-view.fxml'.";
        assert repeatNewPasswordTF != null : "fx:id=\"repeatNewPasswordTF\" was not injected: check your FXML file 'changepassword-view.fxml'.";
    }


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {

        int userID = model.getLoggedInUser().getId();
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

        if (isValidate() && checkPassword()) {

            String hashedPass = PasswordSecurity.hashPassword(newPasswordTF.getText());
            try {
                userRepositoryJDBC.updatePassword(hashedPass, userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            model.getLoggedInUser().setUserPassword(hashedPass);

            logger.info("newPlainPass: {}", newPasswordTF.getText());
            logger.info("newHashedPass: {}", hashedPass);

            moveToProfilePage();
        }

    }



    private boolean isValidate() {
        if (currentPasswordTF.getText() != null && !(currentPasswordTF.getText().isBlank())
                && newPasswordTF.getText() != null && !(newPasswordTF.getText().isBlank())
                && repeatNewPasswordTF.getText() != null && !(repeatNewPasswordTF.getText().isBlank())) {
            return true;
        }

        JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen",
                "Warnung", JOptionPane.ERROR_MESSAGE, null);

        return false;
    }



    private boolean checkPassword() {

        if(PasswordSecurity.isPasswordValid(newPasswordTF.getText())) {

            if (newPasswordTF.getText().equals(repeatNewPasswordTF.getText())) {

                if(!currentPasswordTF.getText().equals(newPasswordTF.getText())) {
                    return true;
                }
                JOptionPane.showMessageDialog(null, "Das neue Passwort kann nicht wie alte Passwort sein",
                        "Warnung", JOptionPane.WARNING_MESSAGE, null);
                return false;
            }
            JOptionPane.showMessageDialog(null, "Bitte neues Passwort nochmal überprüfen",
                    "Warnung", JOptionPane.WARNING_MESSAGE, null);
            return false;
        }
        JOptionPane.showMessageDialog(null,
                "Bitte Beachten Sie:\n Das Passwort muss mindestens aus 8 Zeichen,1 Großbuchstabe,\n 1 Kleinbuchstabe, 1 Ziffer und 1 Sonderzeichen bestehen.",
                "Warnung", JOptionPane.WARNING_MESSAGE, null);
        return false;
    }



    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }


    private void moveToProfilePage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();

        JOptionPane.showMessageDialog(null, "Passwort wurde erfolgreich geändert",
                "Info", JOptionPane.INFORMATION_MESSAGE, null);
    }


    private Stage getCurrentStage(){
        return (Stage) currentPasswordTF.getScene().getWindow();
    }












}
