package com.lap.lapproject.controller;

import com.lap.lapproject.model.Location;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.LocationRepositoryJDBC;

import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddLocationController extends BaseController {
    @FXML
    private TextField streetNameTextField;
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextField locationNameTextField;
    @FXML
    private Label locationZipNoticeLabel;


    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();

        String street = streetNameTextField.getText();
        String zip = zipCodeTextField.getText();
        String city = locationNameTextField.getText();

        Location location = new Location(street, zip, city);
        LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();

        if (!street.isBlank() && !zip.isBlank() && !city.isBlank()) { //method is only used if textfield is not empty

            if (listModel.getSelectedLocation() == null) {
                System.out.println("AddLocationController:: onAddBtnClick");

                try {
//                    locationRepositoryJDBC.addLocation(location);//Location is added to a list
                    listModel.locationList.add(location);
                    locationRepositoryJDBC.readAll();

                    listModel.roomList.setAll(roomRepositoryJDBC.readAll());//update roomList
                    listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());//update bookingList

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                moveToLocationPage();
            } else {
                //Update logik
                location = listModel.getSelectedLocation();
                location.setStreet(streetNameTextField.getText());
                location.setZipcode(zipCodeTextField.getText());
                location.setCity(locationNameTextField.getText());
                locationRepositoryJDBC.updateLocation(location);
                listModel.locationList.set(listModel.locationList.indexOf(location), location);

                listModel.roomList.setAll(roomRepositoryJDBC.readAll());
                listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());
                moveToLocationPage();
            }
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }


    private Stage getCurrentStage() {
        System.out.println("AddLocationController:: getCurrentStage");
        return (Stage) locationNameTextField.getScene().getWindow();
    }

    private void moveToLocationPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

    @FXML
    private void initialize() {
        locationZipNoticeLabel.setVisible(false);
        UsabilityMethods.changeListenerForZip(zipCodeTextField, locationZipNoticeLabel);//!
        System.out.println("AddLocationController:: initialize");
        //Update logik
        if (listModel.getSelectedLocation() != null) {
            streetNameTextField.setText(listModel.getSelectedLocation().getStreet());
            zipCodeTextField.setText(listModel.getSelectedLocation().getZipcode());
            locationNameTextField.setText(listModel.getSelectedLocation().getCity());
        }
    }
}
//UsabilityMethods.changeListenerForZip(zipCodeTextField, locationZipNoticeLabel);//!

