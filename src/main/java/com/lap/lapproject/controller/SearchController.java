package com.lap.lapproject.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

import com.calendarfx.view.TimeField;
import com.lap.lapproject.model.BookingModel;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.controller.BaseController.model;

public class SearchController {

    @FXML
    private DatePicker startDateSearchDatePicker;
    @FXML
    private DatePicker endDateSearchDatePicker;

    @FXML
    private TimeField startSearchTime;
    @FXML
    private TimeField searchEndTime;
    @FXML
    private TableView<Room> tableViewRoom;

    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;
    @FXML
    private TableColumn<Room, Integer> sizeColumn;
    @FXML
    private TableColumn<Room, String> locationColumn;
    @FXML
    private TableColumn<Room, String> equipmentColumn;

    private BookingModel bookingModel;

    @FXML
    private Label searchLabel;


    public SearchController() {
    }

    @FXML
    void initialize() {
        searchLabel.setVisible(false);
        UsabilityMethods.changeListenerDataPicker(startDateSearchDatePicker, searchLabel);

        assert startDateSearchDatePicker != null : "fx:id=\"startDateSearchDatePicker\" was not injected: check your FXML file 'search-view.fxml'.";
        assert startSearchTime != null : "fx:id=\"startSearchTime\" was not injected: check your FXML file 'search-view.fxml'.";
        assert searchEndTime != null : "fx:id=\"searchEndTime\" was not injected: check your FXML file 'search-view.fxml'.";

        assert tableViewRoom != null : "fx:id=\"tableViewRoom\" was not injected: check your FXML file 'search-view.fxml'.";
        assert roomNumberColumn != null : "fx:id=\"roomNumberColumn\" was not injected: check your FXML file 'search-view.fxml'.";
        assert sizeColumn != null : "fx:id=\"sizeColumn\" was not injected: check your FXML file 'search-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'search-view.fxml'.";
        assert equipmentColumn != null : "fx:id=\"equipmentColumn\" was not injected: check your FXML file 'search-view.fxml'.";

        startSearchTime.setValue(LocalTime.of(7, 00));
        searchEndTime.setValue(LocalTime.of(19, 00));

        this.bookingModel = new BookingModel(model);

    }

    @FXML
    public void onButtonClickSearch(ActionEvent actionEvent) {
        LocalDate dateStart = null;
        LocalDate dateEnd = null;
        if(startDateSearchDatePicker.getValue() == null || endDateSearchDatePicker.getValue() == null){
            dateStart = LocalDate.now();
            dateEnd =dateStart;
        } else {
            dateStart = startDateSearchDatePicker.getValue();
            dateEnd = endDateSearchDatePicker.getValue();
        }

        LocalTime timeStart = startSearchTime.getValue();
        LocalTime timeEnd = searchEndTime.getValue();

        BookingModel bookingModel = new BookingModel( model);
        bookingModel.loadBookingIntoCalendar();

        boolean validDateTime = isValidDateTimeForSearch(dateStart, dateEnd, timeStart, timeEnd);

        if(validDateTime) {
            listModel.roomList.setAll(bookingModel.findEmptyRooms(dateStart, dateEnd, timeStart, timeEnd));
            updateSearchTable();
        }
    }

    private boolean isValidDateTimeForSearch(LocalDate dateStart, LocalDate dateEnd, LocalTime timeStart, LocalTime timeEnd) {

        try {
            if(dateStart.isAfter(LocalDate.now()) || dateStart.isAfter(dateEnd)){
                searchLabel.setVisible(true);
                searchLabel.setText("Das Datum muss mindestens mit dem heutigen beginnen \nund darf nicht nach dem Enddatum stehen.");
                return false;
            }
            else if( timeStart.isAfter(timeEnd)  ){
                searchLabel.setVisible(true);
                searchLabel.setText("Die Startzeit darf nicht größer sein als die Endzeit");
                return false;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        searchLabel.setVisible(false);
        return true;
    }

    public void updateSearchTable() {
        tableViewRoom.setItems(listModel.sortedRoomList);
        listModel.sortedRoomList.comparatorProperty().bind(tableViewRoom.comparatorProperty());
        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoomNumber()));
        sizeColumn.setCellValueFactory(dataFeatures -> new SimpleObjectProperty<>(dataFeatures.getValue().getSize()));
        locationColumn.setCellValueFactory(dataFeatures -> new SimpleObjectProperty<>(dataFeatures.getValue().getLocation().getStreet()));
        equipmentColumn.setCellValueFactory(dataFeatures -> new SimpleObjectProperty(dataFeatures.getValue().getEquipmentAsString()));

    }


}
