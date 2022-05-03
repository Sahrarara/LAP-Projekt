package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEquipmentController {
    @FXML
    private TextField nameTextField;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        String equipmentName = nameTextField.getText();
        if (!equipmentName.isBlank()){
            //TODO: insert create new equipment function here
        } else {
            QuickAlert.showError("Bitte den Namen des Geräts/der Ausrüstung angeben");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) nameTextField.getScene().getWindow();
    }

}
