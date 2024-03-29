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

// TODO: trainer bzw. admin muss noch angezeigt werden, welcher die buchung erstellt hat.

/**
 * Diese Klasse extends BasseController. Sie wird genutzt um eine Buchung zu erstellen oder upzudaten, dabei werden die Daten an die dafür zuständigen JDBC klassen gesendet
 */
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


    /**
     * Schließt die Anwendung wieder
     *
     * @param actionEvent
     */
    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    /**
     * Wenn alle Felder Ausgefüllt sind, wird eine Buchung mit addNewBooking() angelegt.
     * Wenn die Buchung editiert wird, wird updateSelectedBooking() ausgeführt.
     *
     * @param actionEvent
     * @throws SQLException wenn nicht alle Felder ausgefüllt sind
     */
    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {


        if (locationChoiceBox.getValue() != null && courseNameChoiceBox.getValue() != null &&
                trainerChoiceBox.getValue() != null && roomNumberChoiceBox.getValue() != null &&
                recurrenceChoiceBox.getValue() != null && startDatePicker.getValue() != null &&
                endDatePicker.getValue() != null && timeStartTimeField.getValue() != null &&
                timeEndTimeField.getValue() != null) {
            System.out.println("welches booking wird aufgerufen: "  + listModel.getSelectedBooking());
            if (listModel.getSelectedBooking() == null) {
                addNewBooking();
            } else {
                updateSelectedBooking();
            }

        } else {
            QuickAlert.showError("Bitte fülle alle Felder aus.");
        }


    }

    private boolean isValidBooking( Room room, Trainer trainer, Course courseName, LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {

        return isValidBooking(null, room, trainer, courseName, dateStart, dateEnd, timeStart, timeEnd);
    }


    private boolean isValidBooking(Booking booking, Room room, Trainer trainer, Course courseName, LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {

        bookingModel.loadBookingIntoCalendar();

        List<Room> filterdEmptyRooms = bookingModel.findEmptyRooms(booking, dateStart, dateEnd, timeStart, timeEnd);

        List<Trainer> filterdAvailableTrainer = bookingModel.findAvailableTrainer(booking, dateStart, dateEnd, timeStart, timeEnd);

        List<Course> filterdAvailableCourse = bookingModel.findAvailableCourse(booking, dateStart, dateEnd, timeStart, timeEnd);


        if (!filterdEmptyRooms.contains(room)){
            QuickAlert.showError("Der Raum ist nicht verfügbar.");
            return false;
        }

        if (!filterdAvailableTrainer.contains(trainer)) {
            QuickAlert.showError("Der Trainer ist nicht verfügbar.");
            return false;
        }

        if (!filterdAvailableCourse.contains(courseName)) {
            QuickAlert.showError("Der Kurs ist nicht verfügbar.");
            return false;

        }
        return true;
    }

    /**
     * Sammelt die angegebenen Daten( Raumnummer, Trainer, Kursname, die Art des sich wiederholenden Termins (recurenceChoiseBox),
     * Starttag, Endtag, Startzeit, Endzeit) für die Buchung aus den ChoiceBoxen, DatePicker und TimeField und
     * fügt den Value(Innhalt) über die Klasse Booking hinzu welcher durch eine observableListArray, der bookingList,
     * über das ListModel an das BookingRepositoryJDBC() geschickt un in der DatenBank eingefügt wird.
     */
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


        boolean isValidDateTimeForAddOrUpdateBooking = isValidDateTimeForAddOrUpdateBooking(dateStart, dateEnd, timeStart, timeEnd);
        boolean isValidBooking = isValidBooking(room, trainer, course, dateStart, dateEnd, timeStart, timeEnd);

        if (isValidDateTimeForAddOrUpdateBooking && isValidBooking) {
            localDateTimeStart = LocalDateTime.of(dateStart, timeStart);
            localDateTimeEnd = LocalDateTime.of(dateEnd, timeEnd);
            Booking booking = new Booking(room, trainer, model.getLoggedInUser(), course, localDateTimeStart, localDateTimeEnd, recurrenceRule);
            listModel.bookingList.add(booking);
            moveToBookingPage();
        }
    }

    /**
     * Es werden die Daten aus der Datenbank gelesen, die neuen Daten in die Datenbank eingetragen und die Anwedung geschlossen.
     */
    private void updateSelectedBooking() {

        Booking selectedBooking = listModel.getSelectedBooking();

        Room room = roomNumberChoiceBox.getValue();
        Trainer trainer = trainerChoiceBox.getValue();
        Course course = courseNameChoiceBox.getValue();

        selectedBooking.setRoom(roomNumberChoiceBox.getValue());
        selectedBooking.setTrainer(trainerChoiceBox.getValue());
        selectedBooking.setCourse(courseNameChoiceBox.getValue());
        selectedBooking.setUser(model.getLoggedInUser());

        LocalDate dateStart = startDatePicker.getValue();
        LocalDate dateEnd = endDatePicker.getValue();
        LocalTime timeStart = timeStartTimeField.getValue();
        LocalTime timeEnd = timeEndTimeField.getValue();

        LocalDateTime localDateTimeStart;
        LocalDateTime localDateTimeEnd;

        selectedBooking.setRecurrenceRule(recurrenceChoiceBox.getValue());
        boolean isValidDateTimeForAddOrUpdateBooking = isValidDateTimeForAddOrUpdateBooking(dateStart, dateEnd, timeStart, timeEnd);
        boolean isValidBooking = isValidBooking(selectedBooking, room, trainer, course, dateStart, dateEnd, timeStart, timeEnd);

        if (isValidDateTimeForAddOrUpdateBooking && isValidBooking) {
            localDateTimeStart = LocalDateTime.of(dateStart, timeStart);
            localDateTimeEnd = LocalDateTime.of(dateEnd, timeEnd);
            selectedBooking.setDateTimeStart(localDateTimeStart);
            selectedBooking.setDateTimeEnd(localDateTimeEnd);

            listModel.bookingList.set(listModel.bookingList.indexOf(selectedBooking), selectedBooking);
            moveToBookingPage();
        }
    }

    /**
     * Prüft den Zustand der InputFelder des AddBookingControllers und befüllt die ChoiceBoxes mit den standart Werten,
     * wenn es kein SelectedBooking ist. Sonst wird die Methode fillChoiceBoxesForUpdate() aufgerufen.
     */
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

    /**
     * Befüllt die leeren ChoiceBoxes mit den Daten ( Standort, Raumnummer, Kursname, Trainername, Serientermin,
     * Startdatum, Enddatum, Startzeit, Endzeit)
     */
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


    /**
     * Befüllt die ChoiceBox des Serientermin mit Wiederholungsarten.
     * Die Standardeinstellung ist "Einzeltermin"
     */
    private void fillRecurrenceRuleChoiceBox() {
        recurrenceChoiceBox.getItems().add("Einzeltermin");
        recurrenceChoiceBox.getItems().add("Täglich");
        recurrenceChoiceBox.getItems().add("Wöchentlich");
        recurrenceChoiceBox.getItems().add("Monatlich");
        if (listModel.getSelectedBooking() == null) {
            recurrenceChoiceBox.setValue("Einzeltermin");
        }
    }

    /**
     * Befüllt die ChoiceBox des Vortragenden von der Klasse Trainer mit einen Trainer
     */
    private void fillActiveTrainerToChoiceBox() {
        listModel.activeTrainerList.clear();
        for (Trainer trainer : listModel.trainerList) {
            if (trainer.getActiveStatus()) {
                listModel.activeTrainerList.add(trainer);
            }
        }
        trainerChoiceBox.setItems(listModel.activeTrainerList);
    }

    /**
     * Befüllt die ChoiceBox für Raumnummer, mit einer Raumnummer
     */
    private void fillRoomChoiceBox() {
        FilteredList<Room> roomFilteredList = new FilteredList<>(listModel.roomList);
        roomNumberChoiceBox.setItems(listModel.roomList);

        locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
            roomFilteredList.setPredicate(room -> room.getLocation().getStreet().equals(locationChoiceBox.getValue().getStreet()));
        });
        roomNumberChoiceBox.setItems(roomFilteredList);
    }


    /**
     * Prüft Startdatum und Enddatum auf 0 (darf nicht 0 sein)
     * und Startzeit und Endzeit auf 0 (darf nicht 0 sein) beim Erstellen eines Zeitraumes im Datepicker und Timefield
     *
     * @param dateStart initialisiertes Startdatum
     * @param dateEnd   initialisiertes Enddatum
     * @param timeStart initialisierte Startzeit
     * @param timeEnd   initialisierte Endzeit
     * @return true wenn das Datum und die Uhrzeit passend ausgefüllt sind
     */
    private boolean isValidDateTimeForAddOrUpdateBooking(LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {
        LocalTime startTimeOfBookings = LocalTime.of(7, 30);
        LocalTime endTimeOfBookings = LocalTime.of(19, 30);
        if (dateStart.compareTo(dateEnd) > 0 || dateStart.isBefore(LocalDate.now()) || dateEnd.isBefore(LocalDate.now())) {
            QuickAlert.showError("Bitte kontrolliere das Datum.");
            return false;
        }
        if (timeStart.compareTo(timeEnd) > 0 || timeStart.compareTo(timeEnd) == 0 ||
                timeStart.isBefore(startTimeOfBookings) || timeEnd.isAfter(endTimeOfBookings)) {
            QuickAlert.showError("Bitte Prüfe die Zeiten. \nEin Kurs läuft nur in der Uhrzeit zwischen 7:30 und 19:30");
            return false;
        }
        return true;
    }

    /**
     * Holt sich das aktuelle Fenster
     *
     * @return
     */
    private Stage getCurrentStage() {
        return (Stage) courseNameChoiceBox.getScene().getWindow();
    }

    /**
     * Schließt das Staged Fenster
     */
    private void moveToBookingPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}




















