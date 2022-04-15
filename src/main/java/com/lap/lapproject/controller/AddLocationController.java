package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddLocationController {
    @FXML
    private TextField streetNameLabel;
    @FXML
    private TextField postalCodeLabel;
    @FXML
    private TextField cityLabel;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        closeCurrentStage();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
    }

    private Stage getCurrentStage(){
        return (Stage) streetNameLabel.getScene().getWindow();
    }

    private void closeCurrentStage(){
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
