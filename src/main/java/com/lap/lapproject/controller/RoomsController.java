package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.model.UserData;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

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
    private void onAddRoomBtnClick(ActionEvent actionEvent) {
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
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
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
    }

    private void initTableRoom() {
        tableViewRoom.setItems(listModel.roomList);
        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoomNumber()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getSize()));
        streetColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getLocation().getStreet()));
        equipmentColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getEquipment().getDescription()));
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
}
