package com.lap.lapproject.controller;

import com.lap.lapproject.model.*;
import com.lap.lapproject.repos.*;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddRoomController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AddRoomController.class);

    @FXML
    private Button addRoomBtn;
    @FXML
    private Button closeButton;
    @FXML
    private VBox vBoxId;
    @FXML
    private HBox hBox;

    @FXML
    private Label dynamicField;
    @FXML
    private TextField sizeTextField;
    @FXML
    private TextField roomNmbrTextField;
    @FXML
    private ChoiceBox<Location> locationChoiceBox;
    @FXML
    private Label labelLocation;
    @FXML
    private Label roomNumberNoticeLabel;
    @FXML
    private Label roomSizeNoticeLabel;
    @FXML
    private ComboBox<Equipment> equipmentComboBox;
    private ObservableList<Equipment> equipmentPerList = FXCollections.observableArrayList();
    private ArrayList<Equipment> equipmentList = new ArrayList<>();

    RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
    BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();


    @FXML
    void initialize() {
        assert roomNmbrTextField != null : "fx:id=\"roomNmbrTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert sizeTextField != null : "fx:id=\"sizeTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert locationChoiceBox != null : "fx:id=\"locationChoiceBox\" was not injected: check your FXML file 'addroom-view.fxml'.";

        fillDropDownElements();
        labelLocation.setVisible(false);
        roomNumberNoticeLabel.setVisible(false);
        roomSizeNoticeLabel.setVisible(false);

        UsabilityMethods.changeListenerForNumber(roomNmbrTextField, roomNumberNoticeLabel);//!
        UsabilityMethods.changeListenerForNumber(sizeTextField, roomSizeNoticeLabel);//!


        addListenerForEquipmentList();

        fillDataForUpdate();

    }


    private void fillDataForUpdate() {
        //Update logik
        if (listModel.getSelectedRoom() != null) {
            addRoomBtn.setText("SPEICHERN");
            labelLocation.setVisible(true);
            locationChoiceBox.setVisible(false);

            roomNmbrTextField.setText(String.valueOf(listModel.getSelectedRoom().getRoomNumber()));
            sizeTextField.setText(String.valueOf(listModel.getSelectedRoom().getSize()));
            locationChoiceBox.setValue(listModel.getSelectedRoom().getLocation());
            labelLocation.setText(listModel.getSelectedRoom().getLocation().getStreet());

            generateItemDynamicToUpdate(equipmentPerList);

        }
    }

    private void addListenerForEquipmentList() {

        if (listModel.getSelectedRoom() != null) {
//             teil der update logik
            if (listModel.getSelectedRoom().getEquipments() != null) {

                equipmentPerList.setAll(listModel.getSelectedRoom().getEquipments());

            }
        }

        Room room = listModel.getSelectedRoom();

        equipmentPerList.addListener(new ListChangeListener<Equipment>() {
            @Override
            public void onChanged(Change<? extends Equipment> change) {

                while (change.next()) {

                    if (change.wasAdded()) {
                        for (Equipment equipment : change.getAddedSubList()) {
                            logger.info("added: {}", equipment.getDescription());

                            try {
                                // add to database
                                roomRepositoryJDBC.addEquipment(room, equipment);

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (Equipment equipment : change.getRemoved()) {
                            logger.info("deleted: {}", equipment.getDescription());
                            try {
                                // delete from database
                                roomRepositoryJDBC.deleteEquipment(room, equipment);

                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            }
        });
    }


    private void generateItemDynamicToUpdate(List<Equipment> equipmentList) {
        for (Equipment equipment : equipmentList) {

            generateItemDynamic(equipment, equipmentList);

        }
    }


    private void generateItemDynamic(Equipment equipment, List<Equipment> equipmentList) {
        HBox hBox1 = new HBox();
        hBox1.setAlignment(Pos.CENTER_LEFT);

        Button button = new Button();
        button.setText("X");
        button.getStyleClass().add("button-close");

        Label label = new Label();

        label.setText(equipment.getDescription());
        hBox1.getChildren().addAll(button, label);
        vBoxId.getChildren().addAll(hBox1);
        //hBoxTest.getChildren().addAll(hBox1);

        EventHandler<MouseEvent> mouseEventHandler = e -> {

            deleteSelectedItem(equipmentList, label);
            hBox1.getChildren().clear();

        };
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandler);
    }


    private void fillDropDownElements() {
        locationChoiceBox.setItems(listModel.locationList);
        equipmentComboBox.setItems(listModel.equipmentList);
    }


    @FXML
    void select(ActionEvent event) {
        logger.info("SELECT CALLED: {}", equipmentComboBox.getSelectionModel().getSelectedItem().getDescription());
        if (listModel.getSelectedRoom() != null) {


            if (!checkDoubleItemInList(equipmentPerList, equipmentComboBox.getSelectionModel().getSelectedItem().getDescription())) {
                ArrayList<Equipment> myEquipment = new ArrayList<>();

                equipmentPerList.add(equipmentComboBox.getSelectionModel().getSelectedItem()); // ListChangeListemer: INSERT
                myEquipment.add(equipmentComboBox.getSelectionModel().getSelectedItem());

                generateItemDynamicToUpdate(myEquipment);

            } else {
                logger.info("Item exists in the list!");
            }

        } else {

            if (!checkDoubleItemInList(equipmentList,
                    equipmentComboBox.getSelectionModel().getSelectedItem().getDescription())) {
                equipmentList.add(equipmentComboBox.getSelectionModel().getSelectedItem());
                generateItemDynamicToAdd(equipmentComboBox.getSelectionModel().getSelectedItem(), equipmentList);

            } else {
                logger.info("Item exists in the list!");
            }
        }
    }


    private void generateItemDynamicToAdd(Equipment equipment, List<Equipment> equipmentList) {
        generateItemDynamic(equipment, equipmentList);
    }

    private boolean checkDoubleItemInList(List<Equipment> equipmentList, String equipmentName) {
        boolean result = false;
        for (Equipment equipment : equipmentList) {
            if (equipmentName.equals(equipment.getDescription())) {
                result = true;
            }
        }
        return result;
    }


    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {

        String roomNbr = roomNmbrTextField.getText();
        String roomSizeString = sizeTextField.getText();


        if (!roomNbr.isBlank() && !sizeTextField.getText().isBlank() && !(locationChoiceBox.getValue() == null) && !(equipmentComboBox.getItems() == null)) {
            if (listModel.getSelectedRoom() == null) {

                int roomNbrInt = Integer.parseInt(roomNmbrTextField.getText());
                int roomUniqueNumberCount = roomRepositoryJDBC.getRoomCountByRoomNumber(roomNbrInt);
                int roomSize = Integer.parseInt(sizeTextField.getText());
                Location location = locationChoiceBox.getValue();

                Room room = new Room(0, roomNbrInt, roomSize, location, equipmentList);


                /*  try {*/
                if (roomUniqueNumberCount == 0) {


                    roomRepositoryJDBC.addRoomEquipment(room);
                    listModel.roomList.add(room);

                    listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());
                    moveToRoomPage();

                } else {
                    UsabilityMethods.addMessage(roomNumberNoticeLabel, "Dieser Raumnummer existiert schon!");
                }

            /*    } catch (SQLException e) {
                    e.printStackTrace();
                }*/
            } else {

                updateRoomEquipment();

            }
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }


    private void updateRoomEquipment() throws SQLException {
        //Update logik
        Room room = listModel.getSelectedRoom();
        room.setRoomNumber(Integer.parseInt(roomNmbrTextField.getText()));
        room.setSize(Integer.parseInt(sizeTextField.getText()));
        room.setLocation(locationChoiceBox.getValue());
        room.setEquipments(equipmentPerList);

        roomRepositoryJDBC.updateRoom(room);


        //listModel.roomList.setAll(roomRepositoryJDBC.readAll());
        listModel.roomList.set(listModel.roomList.indexOf(room), room);
        // listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());
        moveToRoomPage();
    }


    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }


    private void deleteSelectedItem(List<Equipment> equipmentList, Label label) {
        for (int i = 0; i < equipmentList.size(); i++) {
            Equipment item = equipmentList.get(i);
            if (item.getDescription().equals(label.getText())) {
                equipmentList.remove(item); // ListChangeListener: DELETE
                logger.info("Deleted item: {}", item.getDescription());
            }
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
