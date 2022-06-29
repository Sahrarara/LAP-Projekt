package com.lap.lapproject.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.calendarfx.view.TimeField;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.repos.*;
import com.lap.lapproject.utility.QuickAlert;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.model.ListModel.*;
import static javafx.application.Application.launch;

public class SearchController {

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;



    @FXML
    private ChoiceBox<String>  startTimeSearchChoiceBox;

    @FXML
    private ChoiceBox<String> endTimeSearchChoiceBox;

    @FXML
    private TableView<Room> tableViewRoom;
    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;

    public SearchController() {
    }

    @FXML
    void initialize() {
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'search-view.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'search-view.fxml'.";
        assert endTimeSearchChoiceBox != null : "fx:id=\"endTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert startTimeSearchChoiceBox != null : "fx:id=\"startTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
//Negin........................................................
        ArrayList<String> times = new ArrayList<String>();

        for (int i = 7; i <= 17; i++) {

            DecimalFormat df = new DecimalFormat("00");
            String iFormatted = df.format(i);
            times.add(iFormatted + ":00");
            times.add(iFormatted + ":15");
            times.add(iFormatted + ":30");
            times.add(iFormatted + ":45");

        }

                startTimeSearchChoiceBox.getItems().addAll(times);
                endTimeSearchChoiceBox.getItems().addAll(times);

//..............................................
        // get all existing programs from DB
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        UserRepositoryJDBC userRepo = new UserRepositoryJDBC();
        LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        RoomRepositoryJDBC roomNumberRepo = new RoomRepositoryJDBC();
        //programRepositoryJDBC.getAllPrograms();

    }
    //Negin..........................infxml hinzu......................
    public void findFreeRooms() {
        //ToDo
        // Date != null;
        // Time != null;



        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();

        LocalDate dateStart = startDatePicker.getValue();
        LocalDate dateEnd = endDatePicker.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime timeStart = LocalTime.parse(startTimeSearchChoiceBox.getValue(),formatter);
        LocalTime timeEnd = LocalTime.parse(endTimeSearchChoiceBox.getValue(),formatter);

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

            tableViewRoom.getItems().addAll(freeRooms);
        }
    }

    public void onfindFreeRumBtnClick(ActionEvent event) {

        findFreeRooms();
    }

    //................................................................
}
