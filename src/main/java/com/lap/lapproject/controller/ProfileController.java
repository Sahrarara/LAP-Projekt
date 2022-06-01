package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.SQLException;


public class ProfileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Button photoUploadButton;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private Circle circleView;
    @FXML
    private HBox updateEmailHBox;
    @FXML
    private HBox emailHBox;
    @FXML
    private HBox updatePhoneHBox;
    @FXML
    private HBox phoneHBox;
    @FXML
    private HBox updateDescriptionHBox;
    @FXML
    private HBox descriptionHBox;


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {

        if(!(emailTextField.getText().isBlank()) && !(phoneTextField.getText().isBlank())) {

            int id = model.getLoggedInUser().getId();
            byte[] newImage = model.getLoggedInUser().getPhoto();
            String newEmail = emailTextField.getText();
            String newPhone = phoneTextField.getText();
            String newDescription = descriptionTextArea.getText();

            model.getLoggedInUser().setEmail(newEmail);
            model.getLoggedInUser().setPhoneNmbr(newPhone);
            model.getLoggedInUser().setDescription(newDescription);

            UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
            try {
                userRepositoryJDBC.updateUserProfile(new Trainer(id, newDescription, newPhone, newEmail, newImage));

            } catch (SQLException e) {
                e.printStackTrace();
            }

            standardMode();
            initialize();
        }


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
        assert circleView != null : "fx:id=\"circleView\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneLabel != null : "fx:id=\"phoneLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneTextField != null : "fx:id=\"phoneTextField\" was not injected: check your FXML file 'profile-view.fxml'.";


        standardMode();
        firstnameLabel.setText(model.getLoggedInUser().getfName());
        lastnameLabel.setText(model.getLoggedInUser().getlName());
        emailLabel.setText(model.getLoggedInUser().getEmail());
        phoneLabel.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionLabel.setText(model.getLoggedInUser().getDescription());

        if(model.getLoggedInUser().getPhoto() != null) {
            circleView.setFill(new ImagePattern(imageFromBytes(model.getLoggedInUser().getPhoto())));

        } else {

            try {
                circleView.setFill(new ImagePattern(imageFromBytes(convertToBytes
                        ("src/main/resources/com/lap/lapproject/images/lapproject/images/Sample_User_Icon.png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
            if(file != null) {
                circleView.setFill(new ImagePattern(new Image(new FileInputStream(file))));
                model.getLoggedInUser().setPhoto(convertToBytes(file.getAbsolutePath()));
            }

        } catch (IOException e) {
            System.err.println("File " + file.getAbsolutePath() + " not found");
        }
    }


    public static Image imageFromBytes(byte[] photoBytes) {
        Image image = null;
        if(photoBytes != null && photoBytes.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(photoBytes);
            image = new Image(inputStream);
        }
        return image;
    }


    @FXML
    public byte[] convertToBytes(String pathToImage) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathToImage);
        return fileInputStream.readAllBytes();
    }


    private void updateMode() {
        updateEmailHBox.setVisible(true);
        updatePhoneHBox.setVisible(true);
        updateDescriptionHBox.setVisible(true);
        emailHBox.setVisible(false);
        phoneHBox.setVisible(false);
        descriptionHBox.setVisible(false);

        photoUploadButton.setVisible(true);
        saveButton.setVisible(true);
        editButton.setVisible(false);

        emailTextField.setText(model.getLoggedInUser().getEmail());
        phoneTextField.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionTextArea.setText(model.getLoggedInUser().getDescription());
    }


    private void standardMode() {
        updateEmailHBox.setVisible(false);
        updatePhoneHBox.setVisible(false);
        updateDescriptionHBox.setVisible(false);
        emailHBox.setVisible(true);
        phoneHBox.setVisible(true);
        descriptionHBox.setVisible(true);

        photoUploadButton.setVisible(false);
        saveButton.setVisible(false);
        editButton.setVisible(true);
    }


    private Stage getCurrentStage() {
        return (Stage) firstnameLabel.getScene().getWindow();
    }
}
