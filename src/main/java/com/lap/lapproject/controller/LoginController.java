package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.database.DatabaseUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private Button loginBtn;
    @FXML
    private Button skipBtn;
    @FXML
    private Button forgotPasswordBtn;
    @FXML
    private Label adminKontaktLabel;

    @FXML
    private void onLoginBtnClick(ActionEvent actionEvent) {
        if (checkFieldsFilled()){
            String username = usernameTF.getText();
            String password = passwordTF.getText();
            if (DatabaseUtility.checkUsernamePasswordActiveStatus(username, password)){
                System.out.println("Login successful");
                moveToMainPage();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Benutzername und/oder Passwort falsch!");
                a.show();
                System.out.println("Login failed");
            }
        }
    }

    private boolean checkFieldsFilled() {
        if (usernameTF.getText() == null || usernameTF.getText().isBlank() || passwordTF.getText() == null || passwordTF.getText().isBlank()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Bitte Benutzernamen und Passwort eingeben");
            a.show();
            System.out.println("empty textfields");
            return false;
        }
        System.out.println("non empty textfields");
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameTF.setText(null);
        passwordTF.setText(null);
    }

    private void moveToMainPage(){
        Stage currentStage = this.getCurrentStage();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_BIGSCREEN));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        currentStage.setTitle("Raum Management");
        currentStage.setScene(scene);
        currentStage.show();
    }

    private Stage getCurrentStage(){
        return (Stage) usernameTF.getScene().getWindow();
    }

    @FXML
    private void onSkipBtnClick(ActionEvent actionEvent) {
        moveToMainPage();
    }

    public void onforgotPasswordBtn(ActionEvent actionEvent) {
        forgotPasswordBtn.setVisible(false);
        adminKontaktLabel.setText("Bitte kontaktieren Sie Ihren Admin");
    }

}
