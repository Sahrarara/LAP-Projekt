package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNmbrTextField;
    @FXML
    private TextField photoPathTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private CheckBox emailCheckBox;
    @FXML
    private CheckBox phoneNmbrCheckBox;
    @FXML
    private CheckBox activeCheckBox;
    @FXML
    private CheckBox photoCheckBox;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private ChoiceBox authorizationChoiceBox;
    @FXML
    private CheckBox descriptionCheckBox;
    @FXML
    private TextField usernameTextField;

    @FXML
    private void onFileBtnClick(ActionEvent actionEvent) {
        //TODO: File Explorer öffnen damit man ein Foto/Bild auswählen kann. Der Pfad wird dann in photoPathTextField geschrieben
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        String user = usernameTextField.getText();
        String title = titleTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String authorization = (String) authorizationChoiceBox.getValue();
        String password = passwordTextField.getText(); //TODO: HASHEN!!!
        String email = emailTextField.getText(); //TODO: valid email regex?
        String telephone = phoneNmbrTextField.getText();

        if (!user.isBlank() && !firstName.isBlank() && !lastName.isBlank() && !(authorizationChoiceBox.getValue() == null) && !password.isBlank() && !email.isBlank() && !telephone.isBlank()){
            //TODO: Insert create new User function hier einfügen
        } else {
            QuickAlert.showError("Bitte folgende Felder ausfüllen:\nNutzername\nVorname\nNachname\nAuthorization\npassword\ne-mail\nTelefon");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) firstNameTextField.getScene().getWindow();
    }

}
