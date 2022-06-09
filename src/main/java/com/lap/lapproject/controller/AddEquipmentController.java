package com.lap.lapproject.controller;

import com.lap.lapproject.model.Equipment;
import com.lap.lapproject.repos.EquipmentRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddEquipmentController extends BaseController{
    @FXML
    private TextField nameTextField;

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
                    equipmentRepositoryJDBC.addEquipment(equipment);  //Equipment is added to a list. equipmenzview list

                    listModel.equipmentList.add(equipment);
                    listModel.roomList.setAll(roomRepositoryJDBC.readAll());


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                moveToEquipmentPage();
            } else {
                //Update logik
                equipment = listModel.getSelectedEquipment();
                equipment.setDescription(nameTextField.getText());
                equipmentRepositoryJDBC.updateEquipment(equipment);
                listModel.equipmentList.set(listModel.equipmentList.indexOf(equipment), equipment);
                moveToEquipmentPage();

                listModel.roomList.setAll(roomRepositoryJDBC.readAll());
            }
        } else {
            QuickAlert.showError("Bitte den Namen des Geräts/der Ausrüstung angeben");
        }
    }






    /*String equipmentName = nameTextField.getText();
        if (!equipmentName.isBlank()){
            //TODO: insert create new equipment function here
        } else {
            QuickAlert.showError("Bitte den Namen des Geräts/der Ausrüstung angeben");
        }
    }*/

    private Stage getCurrentStage(){
        return (Stage) nameTextField.getScene().getWindow();
    }

    private void moveToEquipmentPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }




    @FXML
    private void initialize() {
        //Update logik
        if (listModel.getSelectedEquipment() != null) {
            nameTextField.setText(listModel.getSelectedEquipment().getDescription());
        }
    }

}
