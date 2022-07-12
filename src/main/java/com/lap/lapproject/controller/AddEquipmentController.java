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

/**
 * Diese Klasse extends BaseController und fügt dem Equipment (Klasse Equipment) einen String der Datenbank hinzu oder updated einen bereits vorhandenen String
 */
public class AddEquipmentController extends BaseController{
    EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
    @FXML
    private TextField nameTextField;
    @FXML
    private Label equipmentNameNoticeLabel;

    /**
     * Diese Methode prüft das TextFeld ob der Name bereits existiert und setzt die Sichtbarketi des Informations-String auf false
     */
    @FXML
    private void initialize() {
        equipmentNameNoticeLabel.setVisible(false);
        UsabilityMethods.changeListenerInputText(nameTextField, equipmentNameNoticeLabel);

        //Update logik
        if (listModel.getSelectedEquipment() != null) {
            nameTextField.setText(listModel.getSelectedEquipment().getDescription());
        }
    }

    /**
     * Schließt die aktuelle gestagete Anwendung
     * @param actionEvent
     */
    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    /**
     * Diese Methode fügt der EquipmentListe eine weitere Ausstattung hinzu, dabei wird abgefragt ob der Name der Ausstattung bereits existiert
     * wenn nicht, dann wird der Name der Datenbank hinzugefügt. Ist der Name bereits in der Datenbank existent, wird ein Label mit der Information, dass der Name schon vorhanden ist, aufgerufen
     * Im anderen Fall wird der Name des Equipments in der Datenbank upgedated.
     * @param actionEvent es wird kein Parameter übergeben
     * @throws SQLException wenn das Element bereits existiert
     */
    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        Equipment equipment = new Equipment(nameTextField.getText());
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
//                    equipmentRepositoryJDBC.addEquipment(equipment);  //Equipment is added to a list. equipmenzview list

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

    /**
     * holt sich den String aus dem Textfield
     * @return
     */
    private Stage getCurrentStage(){
        return (Stage) nameTextField.getScene().getWindow();
    }

    /**
     * Schließt die aktuelle Anwendung ( macht im Prinzip das gleiche wie der onAbortBtnClick() )
     */
    private void moveToEquipmentPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

    /**
     * Fügt den String vom Textfield durch das ListModel in die Datenbank ein, löscht den String aus dem Textfeld wieder und schließt die Anwendung.
     * @throws SQLException
     */
    private  void updateEquipment() throws SQLException {
        listModel.getSelectedEquipment().setDescription(nameTextField.getText());
//        equipmentRepositoryJDBC.updateEquipment(listModel.getSelectedEquipment());
        listModel.equipmentList.set(listModel.equipmentList.indexOf(listModel.getSelectedEquipment()), listModel.getSelectedEquipment());
        moveToEquipmentPage();

        listModel.roomList.setAll(roomRepositoryJDBC.readAll());//TODO: put later to ListModel
    }

}
