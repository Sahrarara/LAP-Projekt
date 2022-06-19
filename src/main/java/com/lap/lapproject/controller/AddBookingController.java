package com.lap.lapproject.controller;

import com.calendarfx.view.TimeField;
import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.*;

import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AddBookingController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AddBookingController.class);

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
    @FXML
    private Label locationNameLabel;


    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }


    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {

        //TODO: if Bedingung damit Booking nur angelegt wird wenn möglich

        if (locationChoiceBox.getValue() != null && courseNameChoiceBox.getValue() != null &&
                trainerChoiceBox.getValue() != null && roomNumberChoiceBox.getValue() != null &&
                recurrenceChoiceBox.getValue() != null && startDatePicker.getValue() != null &&
                endDatePicker.getValue() != null && timeStartTimeField.getValue() != null &&
                timeEndTimeField.getValue() != null) {

            if (listModel.getSelectedBooking() == null) {
                addNewBooking();
            } else {
                updateSelectedBooking();
            }

        } else {
            QuickAlert.showError("Bitte alle nötigen Felder ausfüllen");
        }
    }


    private void addNewBooking() {

        Room room = roomNumberChoiceBox.getValue();
        Trainer trainer = trainerChoiceBox.getValue();
        Course course = courseNameChoiceBox.getValue();
        String recurrenceRule = String.valueOf(recurrenceChoiceBox.getValue());

        LocalDate dateStart = startDatePicker.getValue();
        LocalDate dateEnd = endDatePicker.getValue();

        LocalTime timeStart = timeStartTimeField.getValue();
        LocalTime timeEnd = timeEndTimeField.getValue();

        LocalDateTime localDateTimeStart = null;
        LocalDateTime localDateTimeEnd = null;

        if (isValidDateTimeForAddBooking(dateStart, dateEnd, timeStart, timeEnd)) {
            localDateTimeStart = LocalDateTime.of(dateStart, timeStart);
            localDateTimeEnd = LocalDateTime.of(dateEnd, timeEnd);
            //Negin...................................
            BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
            if (bookingRepositoryJDBC.isRoomFree(room.getId(), localDateTimeStart, localDateTimeEnd)) {
//..................................
            Booking booking = new Booking(room, trainer, model.getLoggedInUser(), course, localDateTimeStart, localDateTimeEnd, recurrenceRule);
            listModel.bookingList.add(booking);
            moveToBookingPage();

                //Negin.................................
            }else{
                QuickAlert.showError("Raum ist bereits gebucht in diesem Zeitraum ");

            }
        }
//..........................................
        }



    private void updateSelectedBooking() {

        Booking selectedBooking = listModel.getSelectedBooking();

        selectedBooking.setRoom(roomNumberChoiceBox.getValue());
        selectedBooking.setUser(model.getLoggedInUser());
        selectedBooking.setTrainer(trainerChoiceBox.getValue());
        selectedBooking.setCourse(courseNameChoiceBox.getValue());

        LocalDate dateStart = startDatePicker.getValue();
        LocalDate dateEnd = endDatePicker.getValue();
        LocalTime timeStart = timeStartTimeField.getValue();
        LocalTime timeEnd = timeEndTimeField.getValue();

        LocalDateTime localDateTimeStart;
        LocalDateTime localDateTimeEnd;

        selectedBooking.setRecurrenceRule(recurrenceChoiceBox.getValue());

        if (isValidDateTimeForUpdateBooking(dateStart, dateEnd, timeStart, timeEnd)) {
            localDateTimeStart = LocalDateTime.of(dateStart, timeStart);
            localDateTimeEnd = LocalDateTime.of(dateEnd, timeEnd);
            selectedBooking.setDateTimeStart(localDateTimeStart);
            selectedBooking.setDateTimeEnd(localDateTimeEnd);

            // bookingRepositoryJDBC.updateBooking(selectedBooking);
            listModel.bookingList.set(listModel.bookingList.indexOf(selectedBooking), selectedBooking);
            moveToBookingPage();
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
        assert locationNameLabel != null : "fx:id=\"locationNameLabel\" was not injected: check your FXML file 'addbooking-view.fxml'.";

        locationNameLabel.setVisible(false);

        bookerLabel.setText(model.getLoggedInUser().getfName() + " " + model.getLoggedInUser().getlName());

        courseNameChoiceBox.setItems(listModel.courseList);
        locationChoiceBox.setItems(listModel.locationList);
        fillActiveTrainerToChoiceBox();
        fillRecurrenceRuleChoiceBox();
        fillRoomChoiceBox();

        timeStartTimeField.setValue(LocalTime.of(7, 30));
        timeEndTimeField.setValue(LocalTime.of(19, 30));


        if (listModel.getSelectedBooking() != null) {
            fillChoiceBoxesForUpdate();
        }

    }


    private void fillChoiceBoxesForUpdate() {

        locationChoiceBox.setVisible(false);
        locationNameLabel.setVisible(true);
        locationNameLabel.setText(listModel.getSelectedBooking().getRoom().getLocation().getStreet());

        locationChoiceBox.setValue(listModel.getSelectedBooking().getRoom().getLocation());
        courseNameChoiceBox.setValue(listModel.getSelectedBooking().getCourse());
        trainerChoiceBox.setValue(listModel.getSelectedBooking().getTrainer());
        roomNumberChoiceBox.setValue(listModel.getSelectedBooking().getRoom());
        recurrenceChoiceBox.setValue(listModel.getSelectedBooking().getRecurrenceRule());
        startDatePicker.setValue(LocalDate.from(listModel.getSelectedBooking().getDateTimeStart()));
        endDatePicker.setValue(LocalDate.from(listModel.getSelectedBooking().getDateTimeEnd()));
        timeStartTimeField.setValue(listModel.getSelectedBooking().getDateTimeStart().toLocalTime());
        timeEndTimeField.setValue(listModel.getSelectedBooking().getDateTimeEnd().toLocalTime());
    }


    private void fillRecurrenceRuleChoiceBox() {
        recurrenceChoiceBox.getItems().add("keiner");
        recurrenceChoiceBox.getItems().add("täglich");
        recurrenceChoiceBox.getItems().add("wöchentlich");
        recurrenceChoiceBox.getItems().add("monatlich");
        if (listModel.getSelectedBooking() == null) {
            recurrenceChoiceBox.setValue("keiner");
        }
    }


    private void fillActiveTrainerToChoiceBox() {
        listModel.activeTrainerList.clear();
        for (Trainer trainer : listModel.trainerList) {
            if (trainer.getActiveStatus()) {
                listModel.activeTrainerList.add(trainer);
            }
        }
        trainerChoiceBox.setItems(listModel.activeTrainerList);
    }


    private void fillRoomChoiceBox() {
        FilteredList<Room> roomFilteredList = new FilteredList<>(listModel.roomList);
        roomNumberChoiceBox.setItems(listModel.roomList);

        locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            roomFilteredList.setPredicate(room -> room.getLocation().getStreet().equals(locationChoiceBox.getValue().getStreet()));
        });
        roomNumberChoiceBox.setItems(roomFilteredList);
    }

    private boolean isValidDateTimeForUpdateBooking(LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {
        if (dateStart.compareTo(dateEnd) > 0) {
            QuickAlert.showError("Datum passt nicht. Bitte kontrollieren!");
            return false;
        } else {
            if (timeStart.compareTo(timeEnd) > 0 || timeStart.compareTo(timeEnd) == 0) {
                QuickAlert.showError("Uhrzeit passt nicht. Bitte kontrollieren!");
                return false;
            }
            return true;
        }
    }


    private boolean isValidDateTimeForAddBooking(LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {
        if (dateStart.compareTo(dateEnd) > 0 || dateStart.isBefore(LocalDate.now()) || dateEnd.isBefore(LocalDate.now())) {
            QuickAlert.showError("Datum passt nicht. Bitte kontrollieren!");
            return false;
        } else {
            if (timeStart.compareTo(timeEnd) > 0 || timeStart.compareTo(timeEnd) == 0 ||
                    (timeStart.equals(LocalTime.of(7, 30)) && timeEnd.equals(LocalTime.of(19, 30)))) {
                QuickAlert.showError("Uhrzeit passt nicht. Bitte kontrollieren!");
                return false;
            }
            return true;
        }
    }


    private Stage getCurrentStage() {
        return (Stage) courseNameChoiceBox.getScene().getWindow();
    }

    private void moveToBookingPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }
    //Negin..........................infxml hinzu......................
    public void onFindFreeRoomsBtnClick(ActionEvent event) {
        //ToDo
        // Date != null;
        // Time != null;
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();

        LocalDate dateStart = startDatePicker.getValue();
        LocalDate dateEnd = endDatePicker.getValue();
        LocalTime timeStart = timeStartTimeField.getValue();
        LocalTime timeEnd = timeEndTimeField.getValue();

        LocalDateTime localDateTimeStart;
        LocalDateTime localDateTimeEnd;

        localDateTimeStart = LocalDateTime.of(dateStart, timeStart);

        localDateTimeEnd = LocalDateTime.of(dateEnd, timeEnd);

        List<Room> freeRooms = bookingRepositoryJDBC.findFreeRoomsByTime(localDateTimeStart, localDateTimeEnd);
        if (freeRooms.isEmpty()){
            QuickAlert.showError("Keine freien Räume  gefunden");
        }else{
            String freeRoomsString = String.join(", ", freeRooms.toString());
            QuickAlert.showInfo(" Folgende Räume sind frei:\n"+ freeRoomsString);

        }
    }
    //................................................................

}




















