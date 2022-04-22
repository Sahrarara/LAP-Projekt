package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRoomController {
    @FXML
    private TextField roomNameTextField;
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField photoPathTextField;
    @FXML
    private ChoiceBox locationChoiceBox;
    @FXML
    private ChoiceBox equipmentChoiceBox;
    @FXML
    private TextField roomNmbrTextField;

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
        return (Stage) roomNameTextField.getScene().getWindow();
    }

}
