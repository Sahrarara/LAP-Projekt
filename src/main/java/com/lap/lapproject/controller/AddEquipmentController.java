package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEquipmentController {
    @FXML
    private Button abbortBtn;
    @FXML
    private TextField amountLabel;
    @FXML
    private TextField nameLabel;

    @FXML
    private void onAbbortBtnClick(ActionEvent actionEvent) {
        closeCurrentStage();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
    }

    private Stage getCurrentStage(){
        return (Stage) abbortBtn.getScene().getWindow();
    }

    private void closeCurrentStage(){
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
