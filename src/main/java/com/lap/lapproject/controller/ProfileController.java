package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.UsabilityMethods;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class ProfileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
    UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

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
    private Button photoDeleteButton;
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
    private Label phoneNoticeLabel;
    @FXML
    private Label emailNoticeLabel;
    @FXML
    private Circle photoDeleteCircle;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button abortButton;


    @FXML
    private void onSaveBtnClick(ActionEvent actionEvent) throws SQLException {

        if (!(emailTextField.getText().isBlank()) && UsabilityMethods.isEmailValid(emailTextField.getText()) &&
                !(phoneTextField.getText().isBlank()) && UsabilityMethods.isPhoneNumberValid(phoneTextField.getText())) {

            int id = model.getLoggedInUser().getId();
            byte[] newImage = model.getLoggedInUser().getPhoto();
            String newEmail = emailTextField.getText();
            String newPhone = phoneTextField.getText();
            String newDescription = descriptionTextArea.getText();

            model.getLoggedInUser().setEmail(newEmail);
            model.getLoggedInUser().setPhoneNmbr(newPhone);
            model.getLoggedInUser().setDescription(newDescription);
            Boolean photoVisibility = model.getLoggedInUser().getPhotoVisibility();
            UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
            try {
                userRepositoryJDBC.updateUserProfile(new Trainer(id, newDescription, newPhone, newEmail, newImage, photoVisibility));
              // SidebarController.bannerImg.setFill(new ImagePattern(imageFromBytes(model.getLoggedInUser().getPhoto())));

            } catch (SQLException e) {
                e.printStackTrace();
            }

            standardMode();
            initialize();
        } else {

            JOptionPane.showMessageDialog(null, "Bitte alle Felder korrekt ausfüllen",
                    "Warnung", JOptionPane.ERROR_MESSAGE, null);
        }

    }

    @FXML
    void initialize() throws SQLException {
        assert abortButton != null : "fx:id=\"abortButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert changePasswordButton != null : "fx:id=\"changePasswordButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert circleView != null : "fx:id=\"circleView\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert descriptionHBox != null : "fx:id=\"descriptionHBox\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert editButton != null : "fx:id=\"editButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailHBox != null : "fx:id=\"emailHBox\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailNoticeLabel != null : "fx:id=\"emailNoticeLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailTextField != null : "fx:id=\"emailTextField\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert firstnameLabel != null : "fx:id=\"firstnameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert lastnameLabel != null : "fx:id=\"lastnameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneHBox != null : "fx:id=\"phoneHBox\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneLabel != null : "fx:id=\"phoneLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneNoticeLabel != null : "fx:id=\"phoneNoticeLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneTextField != null : "fx:id=\"phoneTextField\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert photoDeleteButton != null : "fx:id=\"photoDeleteButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert photoDeleteCircle != null : "fx:id=\"photoDeleteCircle\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert photoUploadButton != null : "fx:id=\"photoUploadButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert updateDescriptionHBox != null : "fx:id=\"updateDescriptionHBox\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert updateEmailHBox != null : "fx:id=\"updateEmailHBox\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert updatePhoneHBox != null : "fx:id=\"updatePhoneHBox\" was not injected: check your FXML file 'profile-view.fxml'.";


        standardMode();
        firstnameLabel.setText(model.getLoggedInUser().getfName());
        lastnameLabel.setText(model.getLoggedInUser().getlName());
        emailLabel.setText(model.getLoggedInUser().getEmail());
        phoneLabel.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionLabel.setText(model.getLoggedInUser().getDescription());

        if (model.getLoggedInUser().getPhoto() != null) {
            circleView.setFill(new ImagePattern(imageFromBytes(model.getLoggedInUser().getPhoto())));
            listModel.setUserImgProperty(imageFromBytes(model.getLoggedInUser().getPhoto()));
            listModel.trainerList.setAll(userRepositoryJDBC.readAllTrainer());//TODO: put later to ListModel
        } else {
            try {
                circleView.setFill(new ImagePattern(imageFromBytes(convertToBytes
                        ("src/main/resources/com/lap/lapproject/images/lapproject/images/user.png"))));
                listModel.setUserImgProperty(imageFromBytes(convertToBytes
                        ("src/main/resources/com/lap/lapproject/images/lapproject/images/user.png")));
                listModel.trainerList.setAll(userRepositoryJDBC.readAllTrainer());//TODO: put later to ListModel
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void onEditBtnClick(ActionEvent actionEvent) {
        updateMode();
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        standardMode();
    }


    @FXML
    private void onChangePasswordBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CHANGE_PASSWORD));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void onPhotoUploadBtnClick(ActionEvent actionEvent) {

        String[] extensionList = {"jpg", "jpeg", "gif", "png"};

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(getCurrentStage());

        int index = file.getAbsolutePath().lastIndexOf('.');

        try {
            if (index > 0) {
                String extension = file.getAbsolutePath().substring(index + 1).toLowerCase(Locale.ROOT);

                if (Arrays.asList(extensionList).contains(extension)) {
                    circleView.setFill(new ImagePattern(new Image(new FileInputStream(file))));
                    model.getLoggedInUser().setPhoto(convertToBytes(file.getAbsolutePath()));
                    System.out.println("File extension is: " + extension);
                } else {
                    JOptionPane.showMessageDialog(null, "Es können folgende Dateiformate hochgeladen werden: \n" +
                                    "JPG, JPEG, GIF, PNG",
                            "Warnung", JOptionPane.WARNING_MESSAGE, null);
                }
            }
        } catch (IOException e) {
            System.err.println("File " + file.getAbsolutePath() + " not found");
        }
    }


    @FXML
    private void onPhotoDeleteButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        circleView.setFill(new ImagePattern(imageFromBytes(convertToBytes
                ("src/main/resources/com/lap/lapproject/images/lapproject/images/user.png"))));
        model.getLoggedInUser().setPhoto(null);

        listModel.trainerList.setAll(userRepositoryJDBC.readAllTrainer());//TODO: put later to ListModel
    }


    public static Image imageFromBytes(byte[] photoBytes) {
        Image image = null;
        if (photoBytes != null && photoBytes.length > 0) {
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
        photoDeleteButton.setVisible(true);
        photoDeleteCircle.setVisible(true);
        abortButton.setVisible(true);
        changePasswordButton.setVisible(false);
        saveButton.setVisible(true);
        editButton.setVisible(false);

        emailTextField.setText(model.getLoggedInUser().getEmail());
        phoneTextField.setText(String.valueOf(model.getLoggedInUser().getPhoneNmbr()));
        descriptionTextArea.setText(model.getLoggedInUser().getDescription());

        UsabilityMethods.changeListenerForPhoneNr(phoneTextField, phoneNoticeLabel);
        UsabilityMethods.changeListenerForEmail(emailTextField, emailNoticeLabel);
    }


    private void standardMode() {
        updateEmailHBox.setVisible(false);
        updatePhoneHBox.setVisible(false);
        updateDescriptionHBox.setVisible(false);
        emailHBox.setVisible(true);
        phoneHBox.setVisible(true);
        descriptionHBox.setVisible(true);

        photoUploadButton.setVisible(false);
        photoDeleteButton.setVisible(false);
        photoDeleteCircle.setVisible(false);
        phoneNoticeLabel.setVisible(false);
        emailNoticeLabel.setVisible(false);

        abortButton.setVisible(false);
        changePasswordButton.setVisible(true);
        saveButton.setVisible(false);
        editButton.setVisible(true);
    }


    private Stage getCurrentStage() {
        return (Stage) firstnameLabel.getScene().getWindow();
    }


}
