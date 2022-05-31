package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.repos.EquipmentRepositoryJDBC;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class EquipmentController extends BaseController{
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
    private TableColumn<Equipment, String> equipmentNameColumn;

    @FXML
    private void onAddEquipmentBtnClick(ActionEvent actionEvent) {
        tableViewEquipment.getSelectionModel().select(null);
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
        EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
        int myIndex = tableViewEquipment.getSelectionModel().getSelectedIndex();

        Equipment equipment1 = tableViewEquipment.getSelectionModel().getSelectedItem();


        try {
            equipmentRepositoryJDBC.deleteEquipment(equipment1);
            listModel.equipmentList.remove(equipment1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
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
    private void onSearchBarClick(ActionEvent actionEvent) {
    }

    @FXML
    private void initialize() {
        assert tableViewEquipment != null : "fx:id=\"tableViewEquipment\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert equipmentNameColumn != null : "fx:id=\"equipmentNameColumn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert addEquipmentBtn != null : "fx:id=\"addEquipmentBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert deleteEquipmentBtn != null : "fx:id=\"deleteEquipmentBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert equipmentBtnBar != null : "fx:id=\"equipmentBtnBar\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";

        authorityVisibility();
        initEquipmentTable();



        listModel.selectedEquipmentProperty().bind(tableViewEquipment.getSelectionModel().selectedItemProperty());

        //TODO: add a Textfield ID and imageView ID in the fxml file for the searchbar and magnifying glass
        //TODO: write the UsabilityMethod.changeListener method in here with the IDs of the searchbar and magnifying glass (you can look it up in ProgramController)
    }

    private void initEquipmentTable() {
        tableViewEquipment.setItems(listModel.equipmentList);
        equipmentNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDescription()));
    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
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
