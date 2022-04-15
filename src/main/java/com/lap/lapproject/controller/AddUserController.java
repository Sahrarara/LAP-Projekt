package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddUserController {
    @FXML
    private Button FileBtn;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private TextField photoTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private CheckBox emailCheckbox;
    @FXML
    private CheckBox telephoneCheckbox;
    @FXML
    private CheckBox activeCheckbox;
    @FXML
    private CheckBox photoCheckbox;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        closeCurrentStage();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onFileBtnClick(ActionEvent actionEvent) {
    }


    private Stage getCurrentStage(){
        return (Stage) FileBtn.getScene().getWindow();
    }

    private void closeCurrentStage(){
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}

