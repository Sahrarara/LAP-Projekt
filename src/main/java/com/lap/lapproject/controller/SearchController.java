package com.lap.lapproject.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.lap.lapproject.model.Room;
import com.lap.lapproject.repos.*;
import com.lap.lapproject.utility.QuickAlert;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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


    // Attribute
    RoomsController model = new RoomsController();

    //Tablle
    @FXML
    private TableView<Room> tableViewRoom;
    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;
    @FXML
    private  TableColumn<Room,Integer> sizeColumn;
    @FXML
    private TableColumn<Room,Integer> streetColumn;
    @FXML
    private TableColumn<Room,Integer> equipmentColumn;



 /*   @Override
 public void initialize(URL location, ResourceBundle resources) {
    }*/
    public   SearchController() {
    }

    @FXML
    void initialize() {
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'search-view.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'search-view.fxml'.";
        assert endTimeSearchChoiceBox != null : "fx:id=\"endTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert startTimeSearchChoiceBox != null : "fx:id=\"startTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        //TableView konfigurieren:

        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        //streetColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        //equipmentColumn.setCellValueFactory(new PropertyValueFactory<>("equipments"));

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
            tableViewRoom.getItems().clear();
            tableViewRoom.getItems().addAll(freeRooms);
        }
    }

    public void onfindFreeRumBtnClick(ActionEvent event) {
        findFreeRooms();
    }



    //................................................................
}
