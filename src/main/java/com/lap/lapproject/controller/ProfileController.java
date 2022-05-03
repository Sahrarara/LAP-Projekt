package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private ImageView profilePictureImgView;
    @FXML
    private Label pathToImageLabel;
    @FXML
    private TextField emailLabel;
    @FXML
    private TextField telephoneLabel;
    @FXML
    private TextArea descriptionLabel;
    @FXML
    private ImageView pathToImage;

    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {
        String email = emailLabel.getText();
        String telephoneNmbr = telephoneLabel.getText();
        String description = descriptionLabel.getText();

        //TODO: Insert update User function here. It only updates the currently logged in User in the database.

        UserData.email = email;         //UserData will be updated after a click on the save button.
        UserData.telephoneNmbr = telephoneNmbr;
        UserData.description = description;
    }

    @FXML
    private void initialize(){
        firstnameLabel.setText(UserData.firstName);
        lastnameLabel.setText(UserData.lastName);
        emailLabel.setText(UserData.email);
        telephoneLabel.setText(String.valueOf(UserData.telephoneNmbr));
        descriptionLabel.setText(UserData.description);
    }

    @FXML
    private void onChangePasswordBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CHANGE_PASSWORD));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }
}
