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

            Room room = roomNumberChoiceBox.getValue();
            Trainer trainer = trainerChoiceBox.getValue();
            Course course = courseNameChoiceBox.getValue();
            String recurrenceRule = String.valueOf(recurrenceChoiceBox.getValue());

            String dateStart = null;
            String dateEnd = null;
            try{
                dateStart = String.valueOf(startDatePicker.getValue());
                dateEnd = String.valueOf(endDatePicker.getValue());
            }catch(IllegalArgumentException e) {
                e.printStackTrace();
            }

            LocalDateTime localDateTimeStart = LocalDateTime.parse(dateStart + "T" + timeStartTimeField.getValue());
            LocalDateTime localDateTimeEnd = LocalDateTime.parse(dateEnd + "T" + timeEndTimeField.getValue());

            //TODO: wie kann ich hier die "bookingID" auch mitgeben?
            Booking booking = new Booking(room, trainer, model.getLoggedInUser(), course, localDateTimeStart, localDateTimeEnd, recurrenceRule);

            //TODO: kommt "changeListener" hier????

            BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
            bookingRepositoryJDBC.addBooking(booking);
            listModel.bookingList.add(booking);

            moveToProgramPage();

        } else {
            QuickAlert.showError("Bitte alle nötigen Felder ausfüllen");
        }
    }



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

        bookerLabel.setText(model.getLoggedInUser().getfName()+ " " + model.getLoggedInUser().getlName());

        recurrenceChoiceBox.getItems().add("keiner");
        recurrenceChoiceBox.getItems().add("täglich");
        recurrenceChoiceBox.getItems().add("wöchentlich");
        recurrenceChoiceBox.getItems().add("monatlich");
        recurrenceChoiceBox.setValue("keiner");

        locationChoiceBox.setItems(listModel.locationList);
        trainerChoiceBox.setItems(listModel.trainerList);
        courseNameChoiceBox.setItems(listModel.courseList);


        FilteredList<Room> roomFilteredList = new FilteredList<>(listModel.roomList);
        roomNumberChoiceBox.setItems(listModel.roomList);

        locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            roomFilteredList.setPredicate(room -> room.getLocation().getStreet().equals(locationChoiceBox.getValue().getStreet()));

        });
        roomNumberChoiceBox.setItems(roomFilteredList);
    }



    private Stage getCurrentStage(){
        return (Stage) locationLabel.getScene().getWindow();
    }

    private void moveToProgramPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
