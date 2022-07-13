package com.lap.lapproject.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.calendarfx.view.TimeField;
import com.lap.lapproject.model.Booking;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.repos.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.util.Callback;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.model.ListModel.*;

public class SearchController {

    @FXML
    private DatePicker dateSearchDatePicker;

    @FXML
    private TimeField startSearchTime;

    @FXML
    private TimeField searchEndTime;
    @FXML
    private TableView<Room> tableViewRoom;

    @FXML
    private Button searchButton;
    @FXML
    private TableColumn roomNumberColumn;
    @FXML
    private TableColumn sizeColumn;
    @FXML
    private TableColumn locationColumn;
    @FXML
    private TableColumn equipmentColumn;


    public SearchController() {
    }



    @FXML
    void initialize() {
        assert dateSearchDatePicker != null : "fx:id=\"dateSearchDatePicker\" was not injected: check your FXML file 'search-view.fxml'.";
        assert startSearchTime != null : "fx:id=\"startSearchTime\" was not injected: check your FXML file 'search-view.fxml'.";
        assert searchEndTime != null : "fx:id=\"searchEndTime\" was not injected: check your FXML file 'search-view.fxml'.";

        assert tableViewRoom != null : "fx:id=\"tableViewRoom\" was not injected: check your FXML file 'search-view.fxml'.";
        assert roomNumberColumn != null : "fx:id=\"roomNumberColumn\" was not injected: check your FXML file 'search-view.fxml'.";
        assert sizeColumn != null : "fx:id=\"sizeColumn\" was not injected: check your FXML file 'search-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'search-view.fxml'.";
        assert equipmentColumn != null : "fx:id=\"equipmentColumn\" was not injected: check your FXML file 'search-view.fxml'.";


        initSearchTable();


        /*
        dateSearchDatePicker.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>()//focus on the TextField object of the DatePicker
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                if (newPropertyValue == false){
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(dateSearchDatePicker.getEditor().getText());
                        sdf.setLenient(false);
                        //if not valid, it will throw ParseException
                        Date date = sdf.parse(dateSearchDatePicker.getEditor().getText());
                        System.out.println(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                        dateSearchDatePicker.getEditor().setText("");
                    }
                }
            };
        });*/

        // get all existing programs from DB
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        UserRepositoryJDBC userRepo = new UserRepositoryJDBC();
        LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        RoomRepositoryJDBC roomNumberRepo = new RoomRepositoryJDBC();
        //programRepositoryJDBC.getAllPrograms();

    }
}
