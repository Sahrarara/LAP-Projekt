package com.lap.lapproject.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.calendarfx.view.TimeField;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.repos.*;
import com.lap.lapproject.utility.QuickAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.model.ListModel.*;

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

        endDatePicker.setDisable(true);
        startTimeSearchChoiceBox.setDisable(true);
        endTimeSearchChoiceBox.setDisable(true);



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
        LocalDate dateEnd = startDatePicker.getValue();
        LocalTime timeStart = LocalTime.of(9,0);//startTimeSearchChoiceBox.getValue();//
        LocalTime timeEnd = LocalTime.of(18,0);//endTimeSearchChoiceBox.getValue();//

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

    public void onSetStartDate(ActionEvent event) {
        endDatePicker.setDisable(false);
        findFreeRooms();
    }

    public void onSetEndDate(ActionEvent event) {
        startTimeSearchChoiceBox.setDisable(false);
    }

    public void onSetStartTime(ActionEvent event) {
        endTimeSearchChoiceBox.setDisable(false);
    }
    //................................................................
}
