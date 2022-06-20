package com.lap.lapproject.controller;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.repos.EquipmentRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddEquipmentController extends BaseController{
    EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
    @FXML
    private TextField nameTextField;
    @FXML
    private Label equipmentNameNoticeLabel;

    @FXML
    private void initialize() {
        equipmentNameNoticeLabel.setVisible(false);
        UsabilityMethods.changeListenerInputText(nameTextField, equipmentNameNoticeLabel);

        //Update logik
        if (listModel.getSelectedEquipment() != null) {
            nameTextField.setText(listModel.getSelectedEquipment().getDescription());
        }
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        Equipment equipment= new Equipment(nameTextField.getText());
        EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();

        RoomRepositoryJDBC roomRepositoryJDBC= new RoomRepositoryJDBC();


        if (!nameTextField.getText().isBlank()) {     //method is only used if textfield is not empty

            if (listModel.getSelectedEquipment() == null) {
                //Add logik
                System.out.println("AddEquipmentController:: onAddBtnClick");
                try {
                    // check  if this Equipment is already in DB
                    int equipmentUniqueDescriptionCount = equipmentRepositoryJDBC.getEquipmentCountByEquipmentDescription(nameTextField.getText());

                    if (equipmentUniqueDescriptionCount == 0) {
                    equipmentRepositoryJDBC.addEquipment(equipment);  //Equipment is added to a list. equipmenzview list

                        listModel.equipmentList.add(equipment);
                        listModel.roomList.setAll(roomRepositoryJDBC.readAll()); //TODO: put later to ListModel
                        moveToEquipmentPage();
                    } else {
                        UsabilityMethods.addMessage(equipmentNameNoticeLabel, "Diese Ausstattung existiert schon!");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                //Update logik
                equipment = listModel.getSelectedEquipment();

                //if
                String newEquipmentName = nameTextField.getText();
                String selectedEquipmentName = listModel.getSelectedEquipment().getDescription();

                if (!newEquipmentName.equals(selectedEquipmentName)) {
                    if ( equipmentRepositoryJDBC.getEquipmentCountByEquipmentDescription(newEquipmentName)  == 0) {
                        updateEquipment();

                    }else {
                        UsabilityMethods.addMessage(equipmentNameNoticeLabel, "Diese Ausstattung existiert schon!");
                    }
                }else {
                    updateEquipment();
                }
            }
        } else {
            QuickAlert.showError("Bitte den Namen des Geräts/der Ausrüstung angeben");
        }
    }


    private Stage getCurrentStage(){
        return (Stage) nameTextField.getScene().getWindow();
    }


    private void moveToEquipmentPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }


    private  void updateEquipment() throws SQLException {
        listModel.getSelectedEquipment().setDescription(nameTextField.getText());
        equipmentRepositoryJDBC.updateEquipment(listModel.getSelectedEquipment());
        listModel.equipmentList.set(listModel.equipmentList.indexOf(listModel.getSelectedEquipment()), listModel.getSelectedEquipment());
        moveToEquipmentPage();

        listModel.roomList.setAll(roomRepositoryJDBC.readAll());//TODO: put later to ListModel
    }

}
