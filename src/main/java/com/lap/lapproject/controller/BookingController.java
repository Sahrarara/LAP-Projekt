package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Locale;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.controller.BaseController.model;

public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @FXML
    private Button deleteBookingBtn;
    @FXML
    private Button editBookingButton;
    @FXML
    private ButtonBar bookingBtnBar;
    @FXML
    private TableView<Booking> tableViewBooking;
    @FXML
    private TableColumn<Booking, String> locationColumn;
    @FXML
    private TableColumn<Booking, Integer> roomColumn;
    @FXML
    private TableColumn<Booking, String> trainerColumn;
    @FXML
    private TableColumn<Booking, String> dateFromColumn;
    @FXML
    private TableColumn<Booking, String> dateToColumn;
    @FXML
    private TableColumn<Booking, String> dateTimeColumn;
    @FXML
    private TableColumn<Booking, String> recurrenceRuleColumn;
    @FXML
    private TableColumn<Booking, String> courseNameColumn;
    @FXML
    private TextField searchBar;
    @FXML
    private Button closeIconButton;




    @FXML
    private void onAddBookingBtnClick(ActionEvent actionEvent) {
        tableViewBooking.getSelectionModel().select(null);
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_BOOKING));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void onDeleteBookingBtnClick(ActionEvent actionEvent) {
        Booking booking  = tableViewBooking.getSelectionModel().getSelectedItem();
      //  BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
        //bookingRepositoryJDBC.deleteBooking(booking);
        listModel.bookingList.remove(booking);
    }

    @FXML
    private void onEditBtnClick(ActionEvent actionEvent) {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_BOOKING));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        assert bookingBtnBar != null : "fx:id=\"bookingBtnBar\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert closeIconButton != null : "fx:id=\"closeIconButton\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert courseNameColumn != null : "fx:id=\"courseNameColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateFromColumn != null : "fx:id=\"dateFromColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateTimeColumn != null : "fx:id=\"dateTimeColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateToColumn != null : "fx:id=\"dateToColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert deleteBookingBtn != null : "fx:id=\"deleteBookingBtn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert editBookingButton != null : "fx:id=\"editBookingButton\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert recurrenceRuleColumn != null : "fx:id=\"recurrenceRuleColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert roomColumn != null : "fx:id=\"roomColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert tableViewBooking != null : "fx:id=\"tableViewBooking\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert trainerColumn != null : "fx:id=\"trainerColumn\" was not injected: check your FXML file 'booking-view.fxml'.";

        closeIconButton.setVisible(false);
        UsabilityMethods.changeListener(searchBar, closeIconButton);

        authorityVisibility();
        initBookingTable();

        // Damit werden alle Choice-Boxen mit Daten aus der selektierte Tabellenzeile befüllt:
        listModel.selectedBookingProperty().bind(tableViewBooking.getSelectionModel().selectedItemProperty());
    }



    public void initBookingTable() {
        tableViewBooking.setItems(listModel.bookingList);
        courseNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCourse().getCourseName()));
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getLocation().getStreet()));
        roomColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getRoomNumber()));

        trainerColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getTrainer().getfName() + " " +
                dataFeatures.getValue().getTrainer().getlName()));

        recurrenceRuleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRecurrenceRule()));

        dateFromColumn.setCellValueFactory((dataFeatures) ->
                new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toLocalDate().toString()));

        dateToColumn.setCellValueFactory((dataFeatures) ->
                new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeEnd().toLocalDate().toString()));

        dateTimeColumn.setCellValueFactory((dataFeatures) ->
                new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toLocalTime().toString().substring(0, 5)
                        + " - " + dataFeatures.getValue().getDateTimeEnd().toLocalTime().toString().substring(0, 5)));
    }



    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                break;
            case "coach":
                break;
            default:
                bookingBtnBar.setVisible(false);
                break;
        }
    }



    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {
        String searchTerm = searchBar.getText();
        ObservableList<Booking> filteredList = FXCollections.observableArrayList();

        if (searchTerm.equals("")) {
            filteredList = listModel.bookingList;

        } else {

            for (Booking elem : listModel.bookingList) {
                if (
                        elem.getCourse().toString().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getTrainer().getfName().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getTrainer().getlName().toUpperCase().contains(searchTerm.toUpperCase())
                                || String.valueOf(elem.getRoom().getRoomNumber()).contains(searchTerm)
                                || elem.getRoom().getLocation().getStreet().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getRecurrenceRule().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getDateTimeStart().toLocalDate().toString().contains(searchTerm)
                                || elem.getDateTimeEnd().toLocalDate().toString().contains(searchTerm)
                                || elem.getDateTimeStart().toLocalTime().toString().contains(searchTerm)
                                || elem.getDateTimeEnd().toLocalTime().toString().contains(searchTerm)
                ) {
                    filteredList.add(elem);
                }
            }
        }
        tableViewBooking.setItems(filteredList);
    }



    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }






}
