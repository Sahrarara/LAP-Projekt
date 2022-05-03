package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRoomController {
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
        //TODO: File Explorer öffnen damit man ein Foto/Bild auswählen kann. Der Pfad wird dann in photoPathTextField geschrieben
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        String roomName = roomNmbrTextField.getText();
        String location = (String) locationChoiceBox.getValue();
        int roomSize = Integer.parseInt(sizeTextField.getText());
        String Equipment = (String) equipmentChoiceBox.getValue();
        if (!roomName.isBlank() && !sizeTextField.getText().isBlank() && !photoPathTextField.getText().isBlank() && !(locationChoiceBox.getValue() == null) && !(equipmentChoiceBox.getValue() == null)){
            //TODO: insert create new Room function here
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) roomNmbrTextField.getScene().getWindow();
    }

}
