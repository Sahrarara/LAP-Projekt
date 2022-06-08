package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.LocationRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class RoomsController extends BaseController {
    @FXML
    private ButtonBar roomsBtnBar;

    @FXML
    private TableView<Room> tableViewRoom;
    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;
    @FXML
    private TableColumn<Room, Integer> sizeColumn;
    @FXML
    private TableColumn<Room, String> streetColumn;
    @FXML
    private TableColumn<Room, String> equipmentColumn;

    @FXML
    private Button editRoomButton;

    @FXML
    private Button deleteBtn;
    @FXML
    private TextField searchBar;
    @FXML
    private ChoiceBox filterChoiceBox;



    @FXML
    private void onAddRoomBtnClick(ActionEvent actionEvent) {
        tableViewRoom.getSelectionModel().select(null);
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_ROOM));
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
    private void onDeleteRoomBtnClick(ActionEvent actionEvent) {
        // QuickAlert.showError("Möchten Sie dieses Room sicher Löschen?");
        RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
        int myIndex = tableViewRoom.getSelectionModel().getSelectedIndex();

        Room roomToDelete = tableViewRoom.getSelectionModel().getSelectedItem();

        //Alert CONFIRMATION TODO: wenn es möglich nur einen CONFIRMATION Alert für Alle DELETE
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {

        try {
            // check in DB how many bookings use the particular room
            int bookingCountByRoom = bookingRepositoryJDBC.getBookingCountByRoomId(roomToDelete.getId());

            if (bookingCountByRoom == 0) {

                roomRepositoryJDBC.deleteRoom(roomToDelete);
                listModel.roomList.remove(roomToDelete);
            } else {
                QuickAlert.showError("Dieses Raum wird für eine Buchung benötigt, Sie können es nicht löschen! Bearbeiten Sie zuerst Ihre Buchungen!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}


    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
        System.out.println("Pressed Room settings");
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_ROOM));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DEBUG: " +tableViewRoom.getSelectionModel().selectedItemProperty());
        listModel.selectedRoomProperty().bind(tableViewRoom.getSelectionModel().selectedItemProperty());


        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void initialize() {
        assert tableViewRoom != null : "fx:id=\"tableViewRoom\" was not injected: check your FXML file 'rooms-view.fxml'.";
        assert roomNumberColumn != null : "fx:id=\"roomNumberColumn\" was not injected: check your FXML file 'rooms-view.fxml'.";
        assert sizeColumn != null : "fx:id=\"sizeColumn\" was not injected: check your FXML file 'rooms-view.fxml'.";
        assert streetColumn != null : "fx:id=\"streetColumn\" was not injected: check your FXML file 'rooms-view.fxml'.";
        assert equipmentColumn != null : "fx:id=\"equipmentColumn\" was not injected: check your FXML file 'rooms-view.fxml'.";
        assert roomsBtnBar != null : "fx:id=\"roomsBtnBar\" was not injected: check your FXML file 'rooms-view.fxml'.";

        authorityVisibility();
        initTableRoom();
        listModel.selectedRoomProperty().bind(tableViewRoom.getSelectionModel().selectedItemProperty());
    }


    private void initTableRoom() {
        tableViewRoom.setItems(listModel.filteredRoomList);
        for(Room r: listModel.roomList){
            System.out.println(r.toString());
        }
        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoomNumber()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getSize()));
        streetColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getLocation().getStreet()));
        equipmentColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty(dataFeatures.getValue().getEquipmentAsString()));

    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                break;
            case "coach":
                roomsBtnBar.setVisible(false);
                break;
            default:
                roomsBtnBar.setVisible(false);
                break;
        }
    }

    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {listModel.filteredRoomList.setPredicate(room -> room.toString().contains(searchBar.getText()));
    }

    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }
}
