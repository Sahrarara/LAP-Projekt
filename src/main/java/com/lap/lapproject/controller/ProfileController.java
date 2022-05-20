package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ProfileController extends BaseController{
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label telephoneLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Button photoUploadButton;



    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {
        standardMode();
        String email = emailLabel.getText();
        String telephoneNmbr = telephoneLabel.getText();
        String description = descriptionLabel.getText();

        //TODO: Insert update User function here. It only updates the currently logged in User in the database.

        model.getLoggedInUser().setEmail(email);     // logged in user will be updated when clicked on save button
        model.getLoggedInUser().setPhoneNmbr(telephoneNmbr);
        model.getLoggedInUser().setDescription(description);

    }

    @FXML
    void initialize() {
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailTextField != null : "fx:id=\"emailTextField\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert firstnameLabel != null : "fx:id=\"firstnameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert lastnameLabel != null : "fx:id=\"lastnameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert photoUploadButton != null : "fx:id=\"photoUploadButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert telephoneLabel != null : "fx:id=\"telephoneLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert telephoneTextField != null : "fx:id=\"telephoneTextField\" was not injected: check your FXML file 'profile-view.fxml'.";

        standardMode();
        firstnameLabel.setText(model.getLoggedInUser().getfName());
        lastnameLabel.setText(model.getLoggedInUser().getlName());
        emailLabel.setText(model.getLoggedInUser().getEmail());
        telephoneLabel.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionLabel.setText(model.getLoggedInUser().getDescription());

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


    @FXML
    private void onEditBtnClick(ActionEvent actionEvent) {
        updateMode();
    }


    @FXML
    private void onPhotoUploadBtnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(getCurrentStage());
        try {
            profileImageView.setImage(new Image(new FileInputStream(file)));
        } catch (IOException e) {
            System.err.println("File " + file.getAbsolutePath() + " not found");
        }
    }


    private void updateMode() {
        emailLabel.setVisible(false);
        telephoneLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        emailTextField.setVisible(true);
        telephoneTextField.setVisible(true);
        descriptionTextArea.setVisible(true);
        photoUploadButton.setVisible(true);
        emailTextField.setText(model.getLoggedInUser().getEmail());
        telephoneTextField.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionTextArea.setText(model.getLoggedInUser().getDescription());
    }


    private void standardMode() {
        emailLabel.setVisible(true);
        telephoneLabel.setVisible(true);
        descriptionLabel.setVisible(true);
        emailTextField.setVisible(false);
        telephoneTextField.setVisible(false);
        descriptionTextArea.setVisible(false);
        photoUploadButton.setVisible(false);
    }


    private Stage getCurrentStage() {
        return (Stage) firstnameLabel.getScene().getWindow();
    }
}
