package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.controller.BaseController.model;

public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @FXML
    private Button deleteBookingBtn;
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
    private TableColumn<Booking, String> dateTimeStartColumn;
    @FXML
    private TableColumn<Booking, String> dateTimeEndColumn;
    @FXML
    private TableColumn<Booking, String> recurrenceRuleColumn;

    @FXML
    private void onAddBookingBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_BOOKING));
        Scene scene = null;
        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
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
        //logger.info("selectedItem: {}", booking.getRoom());

        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
        bookingRepositoryJDBC.deleteBooking(booking);
        //logger.info("deleteBooking: {}", booking.getId());
        listModel.bookingList.remove(booking);
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    void initialize() {
        assert bookingBtnBar != null : "fx:id=\"bookingBtnBar\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateFromColumn != null : "fx:id=\"dateFromColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateToColumn != null : "fx:id=\"dateToColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateTimeEndColumn != null : "fx:id=\"dateTimeEndColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateTimeStartColumn != null : "fx:id=\"dateTimeStartColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert deleteBookingBtn != null : "fx:id=\"deleteBookingBtn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert recurrenceRuleColumn != null : "fx:id=\"recurrenceRuleColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert roomColumn != null : "fx:id=\"roomColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert tableViewBooking != null : "fx:id=\"tableViewBooking\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert trainerColumn != null : "fx:id=\"trainerColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        authorityVisibility();
        initBookingTable();
        listModel.selectedBookingProperty().bind(tableViewBooking.getSelectionModel().selectedItemProperty());

        //TODO: add a Textfield ID and imageView ID in the fxml file for the searchbar and magnifying glass
        //TODO: write the UsabilityMethod.changeListener method in here with the IDs of the searchbar and magnifying glass (you can look it up in ProgramController)
    }


    public void initBookingTable() {

        tableViewBooking.setItems(listModel.bookingList);
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getLocation().getStreet()));
        roomColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getRoomNumber()));
        trainerColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getTrainer().getfName() + " " +
                dataFeatures.getValue().getTrainer().getlName()));
        recurrenceRuleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRecurrenceRule()));
        dateFromColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toString().substring(0, 10)));
        dateToColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeEnd().toString().substring(0, 10)));
        dateTimeStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toString().substring(11)));
        dateTimeEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeEnd().toString().substring(11)));
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
}
