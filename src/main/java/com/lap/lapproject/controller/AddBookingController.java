package com.lap.lapproject.controller;

import com.calendarfx.view.TimeField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AddBookingController extends BaseController {
    @FXML
    private DatePicker singleDateDatePicker;
    @FXML
    private Label locationLabel;
    @FXML
    private Label bookerLabel;
    @FXML
    private DatePicker recurrenceStartDatePicker;
    @FXML
    private DatePicker recurrenceEndDatePicker;
    @FXML
    private ChoiceBox recurrenceChoiceBox;
    @FXML
    private TimeField singleDateTimeStartTimeField;
    @FXML
    private TimeField singleDateTimeEndTimeField;
    @FXML
    private TimeField recurrenceTimeStartTimeField;
    @FXML
    private TimeField recurrenceTimeEndTimeField;
    @FXML
    private ChoiceBox roomNmbrChoiceBox;
    @FXML
    private ChoiceBox trainerIdChoiceBox;
    @FXML
    private ChoiceBox bookingIdChoiceBox;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        //TODO: Rework addbooking.fxml (eintägiger Kurs vs. wöchentlicher Kurs)
        //TODO: if Bedingung damit Booking nur angelegt wird wenn möglich
        if (true == true){      //ist nur da damit der compiler keinen Fehler wirft. Muss noch geändert werden
            //TODO: Insert Create new Booking function here
        } else {
            QuickAlert.showError("Bitte alle nötigen Felder ausfüllen");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) locationLabel.getScene().getWindow();
    }

}
