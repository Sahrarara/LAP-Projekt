package com.lap.lapproject.controller;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.model.RoomsEquipment;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.repos.RoomsEquipmentRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class AddRoomController extends BaseController{
    @FXML
    private TextField sizeTextField;

    @FXML
    private ChoiceBox<Location> locationChoiceBox;

    @FXML
    private ListView<Equipment> listView;

    @FXML
    private TextField roomNmbrTextField;

    @FXML
    void initialize() {
        assert roomNmbrTextField != null : "fx:id=\"roomNmbrTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert sizeTextField != null : "fx:id=\"sizeTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert locationChoiceBox != null : "fx:id=\"locationChoiceBox\" was not injected: check your FXML file 'addroom-view.fxml'.";
        //assert equipmentComboBox != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert listView != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'addroom-view.fxml'.";

        locationChoiceBox.setItems(listModel.locationList);
        //equipmentComboBox.setItems(listModel.equipmentList);
        listView.setItems(listModel.equipmentList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        String roomNbr = roomNmbrTextField.getText();

        if (!roomNbr.isBlank() && !sizeTextField.getText().isBlank() && !(locationChoiceBox.getValue() == null) && !(listView.getItems() == null)) {
            //TODO: insert create new Room function here
            RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
            RoomsEquipmentRepositoryJDBC roomsEquipmentRepositoryJDBC = new RoomsEquipmentRepositoryJDBC();
            System.out.println("AddRoomController:: onAddBtnClick");

//            int roomId = roomRepositoryJDBC.getRoomIdByRoomNbr(roomNbr);
            int roomNbrInt = Integer.parseInt(roomNmbrTextField.getText());
            int roomSize = Integer.parseInt(sizeTextField.getText());
            Location location = locationChoiceBox.getValue();
            List<Equipment> equipments = listView.getSelectionModel().getSelectedItems(); //.getValue()

            try {
                Room room = new Room(
                        0,
                        roomNbrInt,
                        roomSize,
                        location,
                        equipments);

                roomRepositoryJDBC.addRoom(room);

                List<Equipment> equipmentList = room.getEquipment();
                //List<RoomsEquipment> roomsEquipments = new ArrayList();
                for (Equipment equipment : equipmentList){
                    //roomsEquipments.add(
                    //new RoomsEquipment(room.getId(), equipment.getId())
                    //);
                    RoomsEquipment roomsEquipmentToSave = new RoomsEquipment(room.getId(), equipment.getId());
                    roomsEquipmentRepositoryJDBC.addRoomEquipmentRT(roomsEquipmentToSave);

                }

                listModel.roomList.add(room);
                System.out.println("AddRoomController118::" + room.getRoomNumber());

            } catch (SQLException e) {
                e.printStackTrace();
            }
            moveToRoomPage();
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) roomNmbrTextField.getScene().getWindow();
    }

    private void moveToRoomPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
