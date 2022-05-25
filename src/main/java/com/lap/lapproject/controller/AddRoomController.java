package com.lap.lapproject.controller;

import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.repos.RoomsEquipmentRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AddRoomController extends BaseController {
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
        assert listView != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'addroom-view.fxml'.";

        locationChoiceBox.setItems(listModel.locationList);
        //equipmentComboBox.setItems(listModel.equipmentList);
        listView.setItems(listModel.equipmentList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        //Update logik
        if (listModel.getSelectedRoom() != null) {
            roomNmbrTextField.setText(String.valueOf(listModel.getSelectedRoom().getRoomNumber()));
            sizeTextField.setText(String.valueOf(listModel.getSelectedRoom().getSize()));
            locationChoiceBox.setValue(listModel.getSelectedRoom().getLocation());

            listView.setItems(listModel.equipmentList);
            System.out.println("Selected: " + listModel.getSelectedRoom().getEquipment());
            //listView.setSelectionModel(listModel.getSelectedRoom().getEquipment());

            List<Equipment> selectedEquipments = listModel.getSelectedRoom().getEquipment();
            for (Equipment equipment : selectedEquipments) {
                System.out.println("equipments list: :" + selectedEquipments);
                int index = listModel.equipmentList.indexOf(equipment);
                System.out.println("equipment index: "+ index + " for " + equipment);
                listView.getSelectionModel().select(index);
            }
        }
    }



    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
        RoomsEquipmentRepositoryJDBC roomsEquipmentRepositoryJDBC = new RoomsEquipmentRepositoryJDBC();

        String roomNbr = roomNmbrTextField.getText();

        int roomNbrInt = Integer.parseInt(roomNmbrTextField.getText());
        int roomSize = Integer.parseInt(sizeTextField.getText());
        Location location = locationChoiceBox.getValue();
        List<Equipment> equipments = listView.getSelectionModel().getSelectedItems(); //.getValue()


        Room room = new Room(
                0,
                roomNbrInt,
                roomSize,
                location,
                equipments);



        if (!roomNbr.isBlank() && !sizeTextField.getText().isBlank() && !(locationChoiceBox.getValue() == null) && !(listView.getItems() == null)) {
            if (listModel.getSelectedRoom() == null) {
                try {
                    roomRepositoryJDBC.addRoom(room);
                    List<Equipment> equipmentList = room.getEquipment();

                    for (Equipment equipment : equipmentList) {

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
                //Update logik
                room = listModel.getSelectedRoom();
                room.setRoomNumber(Integer.parseInt(roomNmbrTextField.getText()));
                room.setSize(Integer.parseInt(sizeTextField.getText()));
                room.setLocation(locationChoiceBox.getValue());
                room.setEquipment(listView.getSelectionModel().getSelectedItems());

                List<Equipment> equipmentListOld = room.getEquipment();
                List<Equipment> equipmentList = listView.getSelectionModel().getSelectedItems();
                System.out.println("equipmentlist debug " + equipmentList);

                for (Equipment equipment : equipmentListOld) {//delete old equipmentList
                    RoomsEquipment roomsEquipmentToDelete = new RoomsEquipment(room.getId(), equipment.getId());

                    roomsEquipmentRepositoryJDBC.deleteRoomEquipmentRT(roomsEquipmentToDelete);
                }

                for (Equipment equipment : equipmentList) {//add new equipmentList
                    RoomsEquipment roomsEquipmentToSave = new RoomsEquipment(room.getId(), equipment.getId());
                    System.out.println(roomsEquipmentToSave.getRoomsEquipmentId());
                    roomsEquipmentRepositoryJDBC.addRoomEquipmentRT(roomsEquipmentToSave);
                }


                roomRepositoryJDBC.updateRoom(room);
                listModel.roomList.set(listModel.roomList.indexOf(room), room);
                moveToRoomPage();
            }
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }

    private Stage getCurrentStage() {
        return (Stage) roomNmbrTextField.getScene().getWindow();
    }

    private void moveToRoomPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }
}
