package com.lap.lapproject.controller;

import com.lap.lapproject.application.BCrypt;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddUserController extends BaseController{
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

    private Stage getCurrentStage(){
        return (Stage) firstNameTextField.getScene().getWindow();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {

        String username = usernameTextField.getText(); // TODO: EXISTIERT SCHON IN DB??

        boolean active = activeCheckBox.isSelected();
        String title = titleTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String password = passwordTextField.getText(); // TODO: HASHEN!!!
        String authorization = String.valueOf(authorizationChoiceBox.getValue());
        String description = descriptionTextArea.getText();
        String telephone = phoneNmbrTextField.getText();
        String email = emailTextField.getText(); //TODO: valid email regex?


        String photoPath = photoPathTextField.getText();

        boolean descriptionVisible = descriptionCheckBox.isSelected();
        boolean telephoneVisible = phoneNmbrCheckBox.isSelected();
        boolean emailVisible = emailCheckBox.isSelected();
        boolean photoVisible = photoCheckBox.isSelected();


        //neue User in der datenbank speichern
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

        //userRepositoryJDBC.checkUniqueUsername(username);
        //validateEmail(email);
        //checkPassword(password);



        if (!username.isBlank() && !firstName.isBlank() && !lastName.isBlank()
                && !(authorizationChoiceBox.getValue() == null) &&
                !password.isBlank() && !email.isBlank() && !telephone.isBlank()) {
            //TODO: Insert create new User function hier einfügen
            try {
                Trainer trainer = new Trainer(username, title, active, firstName, lastName, hashPassword(password),
                        authorization, description,
                        telephone, email, convertToBytes(photoPath), descriptionVisible, telephoneVisible, emailVisible, photoVisible);
                userRepositoryJDBC.add(trainer);
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


        ObservableList<String> authorizationName = FXCollections.observableArrayList(
                listModel.authorizationList.stream()
                        .map(authorization -> authorization.getAuthority())
                        .collect(Collectors.toList()));

        authorizationChoiceBox.setItems(authorizationName);
    }

    public byte[] convertToBytes(String pathToImage) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pathToImage);
        byte[] imageBytes = fileInputStream.readAllBytes();
        return imageBytes;
        /*byte[] imageBytes;
        if (!pathToImage.isEmpty()) {
            FileInputStream fileInputStream = new FileInputStream(pathToImage);
            imageBytes = fileInputStream.readAllBytes();
            return imageBytes;
        } else {
            imageBytes  = null;
        }
        return imageBytes;*/
    }

    //Email prüfen
    public boolean validateEmail(String email) {
        Pattern compile = Pattern.compile("[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}");
        Matcher matcher = compile.matcher(email);
        System.out.println(matcher.matches());
        System.out.println("EMAIL: ");
        return true;
    }


    // 1 Großbuchstabe, 1 Kleinbuchstabe, 1 Ziffer, 1 Sonderzeichen enthält und eine Länge von mindestens 8 hat
    // (Password Bedigungen)


    public boolean checkPassword(String password) {
        Pattern compile = Pattern.compile("^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$");
        Matcher matcher = compile.matcher(password);
        System.out.println(matcher.matches());
        System.out.println("PASSWORT: ");
        return true;
    }

    //Passwort haschen
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public boolean encodeHash(String pass, String hashPassword) {
        return BCrypt.checkpw(pass, hashPassword);
    }



}
