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
    private ComboBox<Equipment> equipmentComboBox;
    @FXML
    private ObservableList<Equipment> equipmentPerList = FXCollections.observableArrayList();

    RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
    BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();


    @FXML
    void initialize() {
        assert roomNmbrTextField != null : "fx:id=\"roomNmbrTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert sizeTextField != null : "fx:id=\"sizeTextField\" was not injected: check your FXML file 'addroom-view.fxml'.";
        assert locationChoiceBox != null : "fx:id=\"locationChoiceBox\" was not injected: check your FXML file 'addroom-view.fxml'.";

        fillDropDownElements();
        labelLocation.setVisible(false);

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
        Room room = listModel.getSelectedRoom();

        if (room != null) {
//             teil der update logik
            if (room.getEquipments() != null) {
                equipmentPerList.setAll(room.getEquipments());
            }
        }

        equipmentPerList.addListener(new ListChangeListener<Equipment>() {
            @Override
            public void onChanged(Change<? extends Equipment> change) {

                while (change.next()) {

                    if (change.wasAdded()) {
                        for (Equipment equipment : change.getAddedSubList()) {
                            logger.info("added: {}", equipment.getDescription());

                            try {
                                // add to database
                                assert room != null;
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
                                assert room != null;
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

        EventHandler<MouseEvent> mouseEventHandler = e -> {
            logger.info("Event working");
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
        equipmentPerList.add(equipmentComboBox.getSelectionModel().getSelectedItem()); // ListChangeListemer: INSERT

        generateItemDynamicToAdd(equipmentComboBox.getSelectionModel().getSelectedItem(), equipmentPerList);
    }


    private void generateItemDynamicToAdd(Equipment equipment, List<Equipment> equipmentList) {
        generateItemDynamic(equipment, equipmentList);
    }


    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {

        String roomNbr = roomNmbrTextField.getText();
        String roomSizeString = sizeTextField.getText();

        //check if Room Number INT
        if (!UsabilityMethods.isNumber(roomNbr)) {
            QuickAlert.showError("Bitte geben Raumnummer in Zahlen");
            return;
        }

        //check if Room Size INT
        if (!UsabilityMethods.isNumber(roomSizeString)) {
            QuickAlert.showError("Bitte geben Raumgröße in Zahlen");
            return;
        }

        int roomNbrInt = Integer.parseInt(roomNmbrTextField.getText());
        int roomSize = Integer.parseInt(sizeTextField.getText());
        Location location = locationChoiceBox.getValue();

        Room room = new Room(0, roomNbrInt, roomSize, location, equipmentPerList);


        if (!roomNbr.isBlank() && !sizeTextField.getText().isBlank() && !(locationChoiceBox.getValue() == null) && !(equipmentComboBox.getItems() == null)) {
            if (listModel.getSelectedRoom() == null) {
                try {

                    roomRepositoryJDBC.addRoomEquipment(room);
                    listModel.roomList.add(room);

                    listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());
                    moveToRoomPage();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {

                updateRoomEquipment();

            }
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }


    private void updateRoomEquipment() throws SQLException {
        //Update logik
        Room room;
        room = listModel.getSelectedRoom();
        room.setRoomNumber(Integer.parseInt(roomNmbrTextField.getText()));
        room.setSize(Integer.parseInt(sizeTextField.getText()));
        room.setLocation(locationChoiceBox.getValue());
        room.setEquipments(equipmentPerList);

        roomRepositoryJDBC.updateRoom(room);

        listModel.roomList.set(listModel.roomList.indexOf(room), room);

        listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());
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
                logger.info("DELETED ELEMENT: {}", item.getDescription());
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
