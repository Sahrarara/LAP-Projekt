package com.lap.lapproject.controller;

import com.lap.lapproject.application.BCrypt;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddUserController extends BaseController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private ChoiceBox authorizationChoiceBox;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private CheckBox emailCheckBox;
    @FXML
    private TextField phoneNmbrTextField;
    @FXML
    private CheckBox phoneNmbrCheckBox;
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private TextField photoPathTextField;
    @FXML
    private CheckBox photoCheckBox;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private CheckBox descriptionCheckBox;
    @FXML
    private Label errorEmail;
    @FXML
    private Label errorUsername;

    @FXML
    private void onFileBtnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        // Öffnet einen Öffnen-Dialog
        File file = fileChooser.showOpenDialog(getCurrentStage());

        try {
            //zeigt Pfad ins Fenster
            photoPathTextField.setText(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private Stage getCurrentStage() {
        return (Stage) firstNameTextField.getScene().getWindow();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException, IOException {
        String username = usernameTextField.getText();
        boolean active = activeCheckBox.isSelected();
        String title = titleTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String password = passwordTextField.getText();
        String authorization = String.valueOf(authorizationChoiceBox.getValue());
        String description = descriptionTextArea.getText();
        String telephone = phoneNmbrTextField.getText();
        String email = emailTextField.getText();
        String photoPath = photoPathTextField.getText();
        boolean descriptionVisible = descriptionCheckBox.isSelected();
        boolean telephoneVisible = phoneNmbrCheckBox.isSelected();
        boolean emailVisible = emailCheckBox.isSelected();
        boolean photoVisible = photoCheckBox.isSelected();


        //neue User in der datenbank speichern
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
        Trainer trainer;

        if (!username.isBlank() && !firstName.isBlank() && !lastName.isBlank()
                && !(authorizationChoiceBox.getValue() == null) &&
                !password.isBlank() && !email.isBlank() && !telephone.isBlank()) {

            if (listModel.getSelectedUser() == null) {
                try {
                    checkUser(username);
                    validateEmail(email);
                    trainer = new Trainer(username, title, active, firstName, lastName, password,
                            authorization, description,
                            telephone, email, convertToBytes(photoPath), descriptionVisible, telephoneVisible, emailVisible, photoVisible);
                    userRepositoryJDBC.add(trainer);
                    listModel.trainerList.add(trainer);
                    getCurrentStage().close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                trainer = listModel.getSelectedUser();
                trainer.setUsername(usernameTextField.getText());
                trainer.setActiveStatus(activeCheckBox.isSelected());
                trainer.setTitle(titleTextField.getText());
                trainer.setFirstName(firstNameTextField.getText());
                trainer.setLastName(lastNameTextField.getText());


                trainer.setUserPassword(passwordTextField.getText());


                trainer.setAuthority(String.valueOf(authorizationChoiceBox.getValue()));
                trainer.setPhoneNmbr(phoneNmbrTextField.getText());
                trainer.setEmail(emailTextField.getText());

                trainer.setPhoto(convertToBytes(photoPathTextField.getText()));


                trainer.setDescriptionVisibility(descriptionCheckBox.isSelected());
                trainer.setPhoneNmbrVisibility(phoneNmbrCheckBox.isSelected());
                trainer.setEmailVisibility(emailCheckBox.isSelected());
                trainer.setPhotoVisibility(photoCheckBox.isSelected());

                try {
                    userRepositoryJDBC.updateUser(trainer);
                    listModel.trainerList.set(listModel.trainerList.indexOf(trainer), trainer);
                    // getCurrentStage().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        } else {
            QuickAlert.showError("Bitte folgende Felder ausfüllen:\nNutzername\nVorname\nNachname\nAuthorization\npassword\ne-mail\nTelefon");
        }
    }


    @FXML
    void initialize() {
        assert activeCheckBox != null : "fx:id=\"activeCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert authorizationChoiceBox != null : "fx:id=\"authorizationChoiceBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert descriptionCheckBox != null : "fx:id=\"descriptionCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert emailCheckBox != null : "fx:id=\"emailCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert emailTextField != null : "fx:id=\"emailTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert firstNameTextField != null : "fx:id=\"firstNameTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert lastNameTextField != null : "fx:id=\"lastNameTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert phoneNmbrCheckBox != null : "fx:id=\"phoneNmbrCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert phoneNmbrTextField != null : "fx:id=\"phoneNmbrTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert photoCheckBox != null : "fx:id=\"photoCheckBox\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert photoPathTextField != null : "fx:id=\"photoPathTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";
        assert usernameTextField != null : "fx:id=\"usernameTextField\" was not injected: check your FXML file 'adduser-view.fxml'.";

        fillChoiceBox();
        textLabelInvisible();
        fillFormToUpdate();

    }

    @FXML
    public void fillChoiceBox() {
        ObservableList<String> authorizationName = FXCollections.observableArrayList(
                listModel.authorizationList.stream()
                        .map(authorization -> authorization.getAuthority())
                        .collect(Collectors.toList()));

        authorizationChoiceBox.setItems(authorizationName);
    }

    @FXML
    public void textLabelInvisible() {
        //setzt Label auf unsichtbar
        errorUsername.setVisible(false);
        errorEmail.setVisible(false);
    }

    @FXML
    public void checkUser(String username) throws SQLException {
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
        if (userRepositoryJDBC.checkUniqueUsername(username)) {
            errorUsername.setVisible(true);
            errorUsername.setText("Benutzername existiert bereits");
        } else {
            errorUsername.setVisible(false);
        }
    }

    @FXML
    public byte[] convertToBytes(String pathToImage) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathToImage);
        byte[] imageBytes = fileInputStream.readAllBytes();
        return imageBytes;
    }


    //Email prüfen
    @FXML
    public void validateEmail(String email) {
        Pattern compile = Pattern.compile("[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}");
        Matcher matcher = compile.matcher(email);
        if (!matcher.matches()) {
            errorEmail.setVisible(true);
            errorEmail.setText("Email nicht gültig");
        } else {
            errorEmail.setVisible(false);
        }
    }


    // 1 Großbuchstabe, 1 Kleinbuchstabe, 1 Ziffer, 1 Sonderzeichen enthält und eine Länge von mindestens 8 hat
    // (Password Bedigungen)
    //TINA???
    @FXML
    public boolean checkPassword(String password) {
        Pattern compile = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
        Matcher matcher = compile.matcher(password);
        return matcher.matches();
    }

    @FXML
    public void fillFormToUpdate() {
        if (listModel.getSelectedUser() != null) {
            usernameTextField.setText(listModel.getSelectedUser().getUsername());
            titleTextField.setText(listModel.getSelectedUser().getTitle());
            activeCheckBox.setSelected(listModel.getSelectedUser().getActiveStatus());
            firstNameTextField.setText(listModel.getSelectedUser().getfName());
            lastNameTextField.setText(listModel.getSelectedUser().getlName());
            //passwordTextField.setText(listModel.getSelectedUser().getUserPassword());
            authorizationChoiceBox.setValue(listModel.getSelectedUser().getAuthority());
            descriptionTextArea.setText(listModel.getSelectedUser().getDescription());
            phoneNmbrTextField.setText(listModel.getSelectedUser().getPhoneNmbr());
            emailTextField.setText(listModel.getSelectedUser().getEmail());

            //TODO: byte array auf pfad konvertieren

//            photoPathTextField.setText(listModel.getSelectedUser().getPhoto().toString());


            descriptionCheckBox.setSelected(listModel.getSelectedUser().getDescriptionVisibility());
            phoneNmbrCheckBox.setSelected(listModel.getSelectedUser().getPhoneNmbrVisibility());
            emailCheckBox.setSelected(listModel.getSelectedUser().getEmailVisibility());
            photoCheckBox.setSelected(listModel.getSelectedUser().getPhotoVisibility());
        }

    }






}
