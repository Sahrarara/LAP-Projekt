package com.lap.lapproject.controller;

import com.lap.lapproject.application.BCrypt;
import com.lap.lapproject.model.User;
import com.lap.lapproject.repos.Repository;

import java.security.*;
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
import static com.lap.lapproject.model.Security.getSalt;

public class ChangePasswordController extends Repository {
    @FXML
    private PasswordField currentPasswordTF;
    @FXML
    private PasswordField newPasswordTF;
    @FXML
    private PasswordField repeatNewPasswordTF;


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) throws NoSuchAlgorithmException, SQLException{
        Connection connection = connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String passwordtoHash = newPasswordTF.getText();
        String salt = getSalt();

        String currentHashedPassword = com.lap.lapproject.model.Security.get_SHA_1_SecurePassword(currentPasswordTF.getText(), salt);
        String newHashedPassword = com.lap.lapproject.model.Security.get_SHA_1_SecurePassword(passwordtoHash, salt);
        String username = model.getLoggedInUser().getUsername();
        System.out.println(newHashedPassword);

        if (checkFieldsEmpty() && checkOldPasswordIsNotNewPassword() && checkNewRepeatPassword() && this.checkIfPasswordIsInDatabase()){

            String SQL_UPDATE = "UPDATE users SET password=? WHERE username=? AND password=?";

            try (PreparedStatement preparedStatement = connect().prepareStatement(SQL_UPDATE)){

                preparedStatement.setString(1, newHashedPassword);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, currentHashedPassword);

                preparedStatement.executeUpdate();
            }

            System.out.println("yippie, password is changed");

        }
    }


    //private boolean checkPass = BCrypt.checkpw(plainPassword, hashedPassword);

   // private String hashPassword = BCrypt.hashpw()

    private boolean checkIfPasswordIsInDatabase() {
        String var10000 = model.getLoggedInUser().getUsername();
        
        Connection connection = connect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String SELECT_PASSWORD_FROM_USERNAME = "SELECT * FROM users WHERE username = '" + var10000 + "' AND  password = '" + this.currentPasswordTF.getText() + "'";

        /*try (PreparedStatement preparedStatement = connect().prepareStatement()){

        }
        try {
            statement = connection.prepareStatement(SELECT_PASSWORD_FROM_USERNAME);
            resultSet = statement.executeQuery();
            System.out.println("i am here");
            while(resultSet.next()) {
                System.out.println("i am here2");

                if (resultSet.getString("password").equals(this.currentPasswordTF.getText())) {
                    System.out.println("i am here3");
                    System.out.println("old password is correct");
                    return true;
                }
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }*/

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
