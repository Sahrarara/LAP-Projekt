package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private void onSaveBtnClick(ActionEvent actionEvent) {
    }
}
