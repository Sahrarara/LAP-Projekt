package com.lap.lapproject.controller;

import com.calendarfx.view.TimeField;
import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.*;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.SQLException;
import java.time.LocalDateTime;


public class AddBookingController extends BaseController {

    @FXML
    private Label locationLabel;
    @FXML
    private Label bookerLabel;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<String> recurrenceChoiceBox;
    @FXML
    private TimeField timeStartTimeField;
    @FXML
    private TimeField timeEndTimeField;
    @FXML
    private ChoiceBox<Room> roomNumberChoiceBox;
    @FXML
    private ChoiceBox<Trainer> trainerChoiceBox;
    @FXML
    private ChoiceBox<Course> courseNameChoiceBox;
    @FXML
    private ChoiceBox<Location> locationChoiceBox;

    private static final Logger logger = LoggerFactory.getLogger(AddBookingController.class);

    @FXML
    void initialize() {
        assert bookerLabel != null : "fx:id=\"bookerLabel\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert courseNameChoiceBox != null : "fx:id=\"courseNameChoiceBox\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert locationChoiceBox != null : "fx:id=\"locationChoiceBox\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert locationLabel != null : "fx:id=\"locationLabel\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert recurrenceChoiceBox != null : "fx:id=\"recurrenceChoiceBox\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert timeEndTimeField != null : "fx:id=\"timeEndTimeField\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert timeStartTimeField != null : "fx:id=\"timeStartTimeField\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert roomNumberChoiceBox != null : "fx:id=\"roomNumberChoiceBox\" was not injected: check your FXML file 'addbooking-view.fxml'.";
        assert trainerChoiceBox != null : "fx:id=\"trainerChoiceBox\" was not injected: check your FXML file 'addbooking-view.fxml'.";

        bookerLabel.setText(UserData.firstName + " " + UserData.lastName);

        recurrenceChoiceBox.getItems().add("keiner");
        recurrenceChoiceBox.getItems().add("täglich");
        recurrenceChoiceBox.getItems().add("wöchentlich");
        recurrenceChoiceBox.getItems().add("monatlich");
        recurrenceChoiceBox.setValue("keiner");

        locationChoiceBox.setItems(listModel.locationList);
        //logger.info("Location: {}", listModel.locationList.stream().toList());

        trainerChoiceBox.setItems(listModel.trainerList);
        //logger.info("Location: {}", listModel.trainerList.stream().toList());

        courseNameChoiceBox.setItems(listModel.courseList);
        //logger.info("Location: {}", listModel.courseList.stream().toList());


        FilteredList<Room> roomFilteredList = new FilteredList<>(listModel.roomList);
        roomNumberChoiceBox.setItems(listModel.roomList);
        //logger.info("rooms: {}", listModel.roomList);

        locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            roomFilteredList.setPredicate(room -> room.getLocation().getStreet().equals(locationChoiceBox.getValue().getStreet()));

            //logger.info("selected: {}", locationChoiceBox.getValue().getStreet());
            //logger.info("Filtered rooms: {}", roomFilteredList);

        });
        roomNumberChoiceBox.setItems(roomFilteredList);

    }



    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }



    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {


        //TODO: Rework addbooking.fxml (eintägiger Kurs vs. wöchentlicher Kurs)
        //TODO: if Bedingung damit Booking nur angelegt wird wenn möglich
        //TODO: Datum validieren


        if (!(locationChoiceBox.getValue() == null) &&
        !(courseNameChoiceBox.getValue() == null) &&
        !(trainerChoiceBox.getValue() == null) &&
        !(roomNumberChoiceBox.getValue() == null) &&
        !(recurrenceChoiceBox.getValue() == null) &&
        !(startDatePicker.getValue() == null) &&
        !(endDatePicker.getValue() == null) &&
        !(timeStartTimeField.getValue() == null) &&
        !(timeEndTimeField.getValue() == null)){

            int roomID = roomNumberChoiceBox.getValue().getId();
            int userID = UserData.userID;
            int trainerID = trainerChoiceBox.getValue().getId();
            int courseID = courseNameChoiceBox.getValue().getId();
            String recurrenceRule = String.valueOf(recurrenceChoiceBox.getValue());

            String dateStart = null;
            String dateEnd = null;
            try{
                dateStart = String.valueOf(startDatePicker.getValue());
                dateEnd = String.valueOf(endDatePicker.getValue());
            }catch(IllegalArgumentException e) {
                System.out.println("*****************   error!!!!!!!!!!!!!!!!!! *************************");
            }
//            String dateStart = String.valueOf(recurrenceStartDatePicker.getValue());
//            String dateEnd = String.valueOf(recurrenceEndDatePicker.getValue());

            String timeStart = String.valueOf(timeStartTimeField.getValue());
            String timeEnd = String.valueOf(timeEndTimeField.getValue());

            String datetimeStart = dateStart + "T" + timeStart;
            //logger.info("datetimeStart: {}",datetimeStart);// 2022-05-10T15:46:00

            String datetimeEnd = dateEnd + "T" + timeEnd;
            //logger.info("datetimeEnd: {}",datetimeEnd);// 2022-05-10T18:46:00

            LocalDateTime localDateTimeStart = LocalDateTime.parse(datetimeStart);
            //logger.info("localDateTimeStart: {}", localDateTimeStart);

            LocalDateTime localDateTimeEnd = LocalDateTime.parse(datetimeEnd);
            //logger.info("localDateTimeEnd: {}", localDateTimeEnd);

            BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
            bookingRepositoryJDBC.addBooking(roomID, userID, trainerID, courseID, recurrenceRule, localDateTimeStart, localDateTimeEnd);

            moveToProgramPage();

        } else {
            QuickAlert.showError("Bitte alle nötigen Felder ausfüllen");
        }
    }



    private Stage getCurrentStage(){
        return (Stage) locationLabel.getScene().getWindow();
    }


    private void moveToProgramPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
