package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCourseController {
    @FXML
    private TextField courseNameLabel;
    @FXML
    private ChoiceBox programChoiceBox;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextField courseSize;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        closeCurrentStage();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
    }

    private Stage getCurrentStage(){
        return (Stage) courseNameLabel.getScene().getWindow();
    }

    private void closeCurrentStage(){
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
