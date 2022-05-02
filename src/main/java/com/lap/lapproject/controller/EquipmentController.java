package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.UserData;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class EquipmentController {
    @FXML
    private Button addEquipmentBtn;
    @FXML
    private Button deleteEquipmentBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private ButtonBar equipmentBtnBar;

    @FXML
    private TableView<Equipment> tableViewEquipment;
    @FXML
    private TableColumn<Equipment, Boolean> checkBoxColumn;
    @FXML
    private TableColumn<Equipment, String> equipmentNameColumn;
    @FXML
    private TableColumn<Equipment, String> roomNumberColumn;

    @FXML
    private void onAddEquipmentBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_EQUIPMENT));
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
    private void onDeleteBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {
    }

    @FXML
    private void initialize() {
        assert tableViewEquipment != null : "fx:id=\"tableViewEquipment\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert checkBoxColumn != null : "fx:id=\"checkBoxColumn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert roomNumberColumn != null : "fx:id=\"roomNumberColumn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert equipmentNameColumn != null : "fx:id=\"equipmentNameColumn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert addEquipmentBtn != null : "fx:id=\"addEquipmentBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert deleteEquipmentBtn != null : "fx:id=\"deleteEquipmentBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert equipmentBtnBar != null : "fx:id=\"equipmentBtnBar\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";

        authorityVisibility();
        initEquipmentTable();

    }

    private void initEquipmentTable() {

        for (Equipment item: ListModel.equipmentList) {
            System.out.println(item.getRoom().getRoomNumber());
        }
        tableViewEquipment.setItems(ListModel.equipmentList);
        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getRoomNumber()));
        equipmentNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue
         ().getDescription()));

        /*checkBoxColumn.setCellValueFactory((dataFeatures) -> dataFeatures.getValue().checkedProperty());
        checkBoxColumn.setCellFactory(new Callback<TableColumn<Equipment, Boolean>, TableCell<Equipment, Boolean>>() {
            @Override
            public TableCell<Equipment, Boolean> call(TableColumn<Equipment, Boolean> userBooleanTableColumn) {
                TableCell<Equipment, Boolean> cell = new TableCell<>() {
                    CheckBox checkBox = new CheckBox();
                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if(empty) { //wenn kein inhalt
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(null);
                            setGraphic(checkBox);
                            checkBox.setSelected(value);
                            // System.out.println(checkBox.isSelected());
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
*/

    }

    private void authorityVisibility() {
        String authority = UserData.authority;
        switch (authority) {
            case "admin":
                break;
            case "coach":
                equipmentBtnBar.setVisible(false);
                break;
            default:
                equipmentBtnBar.setVisible(false);
                break;
        }
    }
}
