package com.lap.lapproject.controller;

import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.*;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

import javax.swing.*;
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
    private Label roomNumberNoticeLabel;
    @FXML
    private Label roomSizeNoticeLabel;


    @FXML
    void initialize() {
        assert roomNmbrTextField != null : "fx:id=\"roomNmbrTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert sizeTextField != null : "fx:id=\"sizeTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert locationChoiceBox != null : "fx:id=\"locationChoiceBox\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert listView != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert roomNumberNoticeLabel != null : "fx:id=\"roomNumberNoticeLabel\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert roomSizeNoticeLabel != null : "fx:id=\"roomSizeNoticeLabel\" was not injected: check your FXML file 'addroom-view.fxml'.";

        locationChoiceBox.setItems(listModel.locationList);
        //equipmentComboBox.setItems(listModel.equipmentList);
        listView.setItems(listModel.equipmentList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        roomNumberNoticeLabel.setVisible(false);
        roomSizeNoticeLabel.setVisible(false);

        UsabilityMethods.changeListenerForNumber(roomNmbrTextField, roomNumberNoticeLabel);//!
        UsabilityMethods.changeListenerForNumber(sizeTextField, roomSizeNoticeLabel);//!


        //Update logik
        if (listModel.getSelectedRoom() != null) {
            roomNmbrTextField.setText(String.valueOf(listModel.getSelectedRoom().getRoomNumber()));
            sizeTextField.setText(String.valueOf(listModel.getSelectedRoom().getSize()));
            locationChoiceBox.setValue(listModel.getSelectedRoom().getLocation());

            listView.setItems(listModel.equipmentList);

            List<Equipment> selectedEquipments = listModel.getSelectedRoom().getEquipment();
            for (Equipment equipment : selectedEquipments) {
                int index = listModel.equipmentList.indexOf(equipment);
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
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();


        String roomNbr = roomNmbrTextField.getText();
        String roomSizeString = sizeTextField.getText();

        int roomNbrInt = Integer.parseInt(roomNmbrTextField.getText());
        int roomSize = Integer.parseInt(sizeTextField.getText());

        int roomUniqueNumberCount = roomRepositoryJDBC.getRoomCountByRoomNumber(roomNbrInt);

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
                    // check if this room is already in DB

                    if (roomUniqueNumberCount  == 0) {
                        roomRepositoryJDBC.addRoom(room);
                        List<Equipment> equipmentList = room.getEquipment();

                        for (Equipment equipment : equipmentList) {

                            RoomsEquipment roomsEquipmentToSave = new RoomsEquipment(room.getId(), equipment.getId());
                            roomsEquipmentRepositoryJDBC.addRoomEquipmentRT(roomsEquipmentToSave);
                        }

                        listModel.roomList.add(room);
                        moveToRoomPage();

                        listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());//TODO: put later to ListModel
                    } else {
                        UsabilityMethods.addMessage(roomNumberNoticeLabel, "Dieser Raumnummer existiert schon!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {//Update
                int newRoomNumber = Integer.parseInt(roomNmbrTextField.getText());
                int selectedRoomNumber = listModel.getSelectedRoom().getRoomNumber();

                if (newRoomNumber != selectedRoomNumber) {
                    if (roomUniqueNumberCount == 0) {
                        updateRoom();
                    } else {
                        UsabilityMethods.addMessage(roomNumberNoticeLabel, "Dieser Raumnummer existiert schon!");
                    }
                } else {
                    updateRoom();
                }
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


    private  void  updateRoom() throws SQLException {
        RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
        RoomsEquipmentRepositoryJDBC roomsEquipmentRepositoryJDBC = new RoomsEquipmentRepositoryJDBC();
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();

        listModel.getSelectedRoom().setRoomNumber(Integer.parseInt(roomNmbrTextField.getText()));
        listModel.getSelectedRoom().setSize(Integer.parseInt(sizeTextField.getText()));
        listModel.getSelectedRoom().setLocation(locationChoiceBox.getValue());
        listModel.getSelectedRoom().setEquipment(listView.getSelectionModel().getSelectedItems());
        List<Equipment> equipmentListOld = listModel.getSelectedRoom().getEquipment();
        List<Equipment> equipmentList = listView.getSelectionModel().getSelectedItems();
        System.out.println("equipmentlist debug " + equipmentList);

        for (Equipment equipment : equipmentListOld) {//delete old equipmentList
            RoomsEquipment roomsEquipmentToDelete = new RoomsEquipment(listModel.getSelectedRoom().getId(), equipment.getId());

            roomsEquipmentRepositoryJDBC.deleteRoomEquipmentRT(roomsEquipmentToDelete);
        }

        for (Equipment equipment : equipmentList) {//add new equipmentList
            RoomsEquipment roomsEquipmentToSave = new RoomsEquipment(listModel.getSelectedRoom().getId(), equipment.getId());
            System.out.println(roomsEquipmentToSave.getRoomsEquipmentId());
            roomsEquipmentRepositoryJDBC.addRoomEquipmentRT(roomsEquipmentToSave);
        }

        roomRepositoryJDBC.updateRoom(listModel.getSelectedRoom());
        listModel.roomList.set(listModel.roomList.indexOf(listModel.getSelectedRoom()), listModel.getSelectedRoom());

        listModel.bookingList.setAll(bookingRepositoryJDBC.readAll()); //TODO: put later to ListModel
        moveToRoomPage();
    }
}