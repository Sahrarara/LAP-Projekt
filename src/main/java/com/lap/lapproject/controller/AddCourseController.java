package com.lap.lapproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

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
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        String coursename = courseNameTextField.getText();
        String program = programChoiceBox.getValue().toString();
        LocalDate courseStartDate = courseStartDatePicker.getValue();
        LocalDate courseEndDate = courseEndDatePicker.getValue();
        int groupSize = Integer.parseInt(groupSizeTextField.getText());

        if (!coursename.isBlank() && !program.isBlank() && !(courseStartDate == null) && !(courseEndDate == null) && !groupSizeTextField.getText().isBlank()){
            //TODO: Insert create new Course function here
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) groupSizeTextField.getScene().getWindow();
    }

}
