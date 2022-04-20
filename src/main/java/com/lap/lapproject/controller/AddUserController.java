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
    private void onFileBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
    }

    private Stage getCurrentStage(){
        return (Stage) firstNameTextField.getScene().getWindow();
    }

}
