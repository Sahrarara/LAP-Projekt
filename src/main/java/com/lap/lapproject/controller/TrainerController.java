package com.lap.lapproject.controller;

import com.lap.lapproject.application.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import static com.lap.lapproject.controller.BaseController.model;

public class TrainerController {

    @FXML
    private void onAddTrainerBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_CREATE_NEW_TRAINER);
    }

    @FXML
    private void onDeleteTrainerBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }
}
