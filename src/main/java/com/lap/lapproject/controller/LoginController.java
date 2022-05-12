package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.User;
import com.lap.lapproject.repos.UserRepositoryJDBC;
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

public class LoginController extends BaseController implements Initializable{


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
            User user = UserRepositoryJDBC.checkUsernamePasswordActiveStatus(username, password);
            if (user != null){
                model.setLoggedInUser(user);
                System.out.println("Login successful");
                moveToMainPage();
            } else {
                QuickAlert.showError("Benutzername und/oder Passwort falsch!");
                System.out.println("Login failed");
            }
        }
    }

    private boolean checkFieldsFilled() {
        if (usernameTF.getText() == null || usernameTF.getText().isBlank() || passwordTF.getText() == null || passwordTF.getText().isBlank()) {
            QuickAlert.showError("Bitte Benutzernamen und Passwort eingeben");
            System.out.println("empty textfields");
            return false;
        }
        System.out.println("non empty textfields");
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameTF.setText("capitanMarvel");
        passwordTF.setText("carol123");
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
