package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.repos.EquipmentRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

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
    private TextField searchBar;
    @FXML
    private Button closeIconButton;

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
        if (listModel.getSelectedEquipment() != null) {
            RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
            EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
            int myIndex = tableViewEquipment.getSelectionModel().getSelectedIndex();
            Equipment equipment1 = tableViewEquipment.getSelectionModel().getSelectedItem();

            //Alert CONFIRMATION TODO: wenn es möglich nur einen CONFIRMATION Alert für Alle DELETE
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                try {
                    equipmentRepositoryJDBC.deleteEquipment(equipment1);
                    listModel.equipmentList.remove(equipment1);

                    listModel.roomList.setAll(roomRepositoryJDBC.readAll());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            }else {
            QuickAlert.showInfo("Bitte gewünschte Zeile markieren");
        }
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
        if (listModel.getSelectedEquipment() != null) {
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
        } else {
            QuickAlert.showInfo("Bitte gewünschte Zeile markieren");
        }
    }



    @FXML
    private void initialize() {
        assert tableViewEquipment != null : "fx:id=\"tableViewEquipment\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert equipmentNameColumn != null : "fx:id=\"equipmentNameColumn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert addEquipmentBtn != null : "fx:id=\"addEquipmentBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert deleteEquipmentBtn != null : "fx:id=\"deleteEquipmentBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert equipmentBtnBar != null : "fx:id=\"equipmentBtnBar\" was not injected: check your FXML file 'equipment-view.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'equipment-view.fxml'.";

        closeIconButton.setVisible(false);
        UsabilityMethods.changeListener(searchBar, closeIconButton);

        authorityVisibility();
        initEquipmentTable();

        listModel.selectedEquipmentProperty().bind(tableViewEquipment.getSelectionModel().selectedItemProperty());
    }

    private void initEquipmentTable() {
        tableViewEquipment.setItems(listModel.sortedEquipmentList);
        listModel.sortedEquipmentList.comparatorProperty().bind(tableViewEquipment.comparatorProperty());
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



    @FXML
    private void onSearchBarClick(KeyEvent actionEvent) {
        listModel.filteredEquipmentList.setPredicate(equipment -> equipment.getDescription().toLowerCase(Locale.ROOT).contains(searchBar.getText().toLowerCase(Locale.ROOT)));
    }


    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }
}
