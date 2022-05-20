package com.lap.lapproject.controller;

import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    private Stage getCurrentStage() {
        return (Stage) firstNameTextField.getScene().getWindow();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
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

        checkUser(username);
        validateEmail(email);


        //emptyPath(photoPath);

        //neue User in der datenbank speichern
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

        if (!username.isBlank() && !firstName.isBlank() && !lastName.isBlank()
                && !(authorizationChoiceBox.getValue() == null) &&
                !password.isBlank() && !email.isBlank() && !telephone.isBlank()) {
            try {
                Trainer trainer = new Trainer(username, title, active, firstName, lastName, password,
                        authorization, description,
                        telephone, email, convertToBytes(photoPath), descriptionVisible, telephoneVisible, emailVisible, photoVisible);
                userRepositoryJDBC.add(trainer);
                listModel.trainerList.add(trainer);
                getCurrentStage().close();
            } catch (Exception e) {
                e.printStackTrace();
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

    }

    public void fillChoiceBox() {
        ObservableList<String> authorizationName = FXCollections.observableArrayList(
                listModel.authorizationList.stream()
                        .map(authorization -> authorization.getAuthority())
                        .collect(Collectors.toList()));

        authorizationChoiceBox.setItems(authorizationName);
    }

    public void textLabelInvisible() {
        //setzt Label auf unsichtbar
        errorUsername.setVisible(false);
        errorEmail.setVisible(false);
    }

    public void checkUser(String username) throws SQLException {
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
        if (userRepositoryJDBC.checkUniqueUsername(username)) {
            errorUsername.setVisible(true);
            errorUsername.setText("Benutzername existiert bereits");
        } else {
            errorUsername.setVisible(false);
        }
    }

    public byte[] convertToBytes(String pathToImage) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathToImage);
        byte[] imageBytes = fileInputStream.readAllBytes();
        return imageBytes;
    }


    //Email prüfen
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
    public boolean checkPassword(String password) {
        Pattern compile = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
        Matcher matcher = compile.matcher(password);
        return matcher.matches();
    }









}
