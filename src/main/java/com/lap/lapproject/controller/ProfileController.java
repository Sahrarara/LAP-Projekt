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
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private ImageView profileImageView;
    @FXML
    private Button photoUploadButton;

//    private byte[] bytesFromImage(String imageName) {
//
//        OutputStream outputStream = new ByteArrayOutputStream();
//        byte[] array = imageName.getBytes();
//        try {
//            outputStream.write(array);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        byte[] bytes = ((ByteArrayOutputStream) outputStream).toByteArray();
//        return bytes;
//    }









    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) {

        if(!(emailTextField.getText() == null) && !(phoneTextField.getText() == null)) {

            int id = model.getLoggedInUser().getId();
            String newEmail = emailTextField.getText();
            String newPhone = phoneTextField.getText();
            String newDescription = descriptionTextArea.getText();
            Image image = profileImageView.getImage();


            model.getLoggedInUser().setEmail(newEmail);
            model.getLoggedInUser().setPhoneNmbr(newPhone);
            model.getLoggedInUser().setDescription(newDescription);
//            model.getLoggedInUser().setPhoto(image);
//            profileImageView.getImage();

            UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
            try {
                userRepositoryJDBC.updateUserProfile(new Trainer(id, newDescription, newPhone, newEmail));

            } catch (SQLException e) {
                e.printStackTrace();
            }

            standardMode();
            logger.info("updateUserProfile: {}", model.getLoggedInUser().getEmail());
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
        assert profileImageView != null : "fx:id=\"profileImageView\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneLabel != null : "fx:id=\"phoneLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneTextField != null : "fx:id=\"phoneTextField\" was not injected: check your FXML file 'profile-view.fxml'.";

        standardMode();
        firstnameLabel.setText(model.getLoggedInUser().getfName());
        lastnameLabel.setText(model.getLoggedInUser().getlName());
        emailLabel.setText(model.getLoggedInUser().getEmail());
        phoneLabel.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionLabel.setText(model.getLoggedInUser().getDescription());
        profileImageView.setImage(imageFromBytes(model.getLoggedInUser().getPhoto()));

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
            logger.info("File path: {}", file.getAbsolutePath());
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


    private void updateMode() {
        emailLabel.setVisible(false);
        phoneLabel.setVisible(false);
        descriptionLabel.setVisible(false);
        emailTextField.setVisible(true);
        phoneTextField.setVisible(true);
        descriptionTextArea.setVisible(true);
        photoUploadButton.setVisible(true);
        emailTextField.setText(model.getLoggedInUser().getEmail());
        phoneTextField.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionTextArea.setText(model.getLoggedInUser().getDescription());
    }


    private void standardMode() {
        emailLabel.setVisible(true);
        phoneLabel.setVisible(true);
        descriptionLabel.setVisible(true);
        emailTextField.setVisible(false);
        phoneTextField.setVisible(false);
        descriptionTextArea.setVisible(false);
        photoUploadButton.setVisible(false);
    }


    private Stage getCurrentStage() {
        return (Stage) firstnameLabel.getScene().getWindow();
    }
}
