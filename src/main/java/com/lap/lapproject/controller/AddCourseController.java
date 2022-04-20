package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddCourseController {
    @FXML
    private TextField courseNameTextField;
    @FXML
    private ChoiceBox programChoiceBox;
    @FXML
    private DatePicker courseStartDatePicker;
    @FXML
    private DatePicker courseEndDatePicker;
    @FXML
    private TextField groupSizeTextField;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
    }
}
