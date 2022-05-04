package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Booking;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.UserData;
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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BookingController {
    @FXML
    private Button deleteBookingBtn;
    @FXML
    private ButtonBar bookingBtnBar;
    @FXML
    private TableView<Booking> tableViewBooking;
    @FXML
    private TableColumn<Booking, String> locationColumn;
    @FXML
    private TableColumn<Booking, String> roomColumn;
    @FXML
    private TableColumn<Booking, String> trainerColumn;
    @FXML
    private TableColumn<Booking, String> dateColumn;
    @FXML
    private TableColumn<Booking, String> dateTimeStartColumn;
    @FXML
    private TableColumn<Booking, String> dateTimeEndColumn;


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

        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onDeleteBookingBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    void initialize() {
        assert bookingBtnBar != null : "fx:id=\"bookingBtnBar\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateTimeEndColumn != null : "fx:id=\"dateTimeEndColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateTimeStartColumn != null : "fx:id=\"dateTimeStartColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert deleteBookingBtn != null : "fx:id=\"deleteBookingBtn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert roomColumn != null : "fx:id=\"roomColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert tableViewBooking != null : "fx:id=\"tableViewBooking\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert trainerColumn != null : "fx:id=\"trainerColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        authorityVisibility();
        BookingRepositoryJDBC bookingRepo = new BookingRepositoryJDBC();

        try {
            bookingRepo.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initBookingTable();
    }


    public void initBookingTable() {


        tableViewBooking.setItems(ListModel.bookingList);
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getLocation().getStreet()));
        roomColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getRoomNumber()));
        trainerColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getTrainer().getfName() + " " +
                dataFeatures.getValue().getTrainer().getlName()));
        dateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toString().substring(0, 10)));
        dateTimeStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toString().substring(11)));
        dateTimeEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeEnd().toString().substring(11)));







    }

    private void authorityVisibility() {
        String authority = UserData.authority;
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
