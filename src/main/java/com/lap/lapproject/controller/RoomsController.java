package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.model.UserData;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RoomsController {
    @FXML
    private ButtonBar roomsBtnBar;


    @FXML
    private TableView<Room> tableViewRoom;
    @FXML
    private TableColumn<Room, String> roomNumberColumn;
    @FXML
    private TableColumn<Room, Integer> sizeColumn;
    @FXML
    private TableColumn<Room, String> streetColumn;

    @FXML
    private void onAddRoomBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_ROOM));
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
    private void onDeleteRoomBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void initialize() throws SQLException {
        RoomRepositoryJDBC roomRepo = new RoomRepositoryJDBC();
        authorityVisibility();

        roomRepo.getRoom();
        initTableRoom();
    }

    private void initTableRoom() {
        tableViewRoom.setItems(ListModel.roomList);
        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoomNumber()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getSize()));
        streetColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getLocation().getStreet()));
    }

    private void authorityVisibility(){
        String authority = UserData.authority;
        switch (authority){
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
