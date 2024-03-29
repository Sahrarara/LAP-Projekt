package com.lap.lapproject.controller;

import com.lap.lapproject.RoomsApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.LocationRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.SimpleObjectProperty;
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

public class LocationController extends BaseController {
    @FXML
    private ButtonBar locationBtnBar;

    @FXML
    private Button deleteLocationBtn;

    @FXML
    private Button settingsLocationBtn;

    @FXML
    private TableView<Location> tableViewLocation;
    @FXML
    private TableColumn<Location, String> streetColumn;
    @FXML
    private TableColumn<Location, String> zipColumn;
    @FXML
    private TableColumn<Location, String> cityColumn;
    @FXML
    private ChoiceBox filterChoiceBox;
    @FXML
    private TextField searchBar;
    @FXML
    private Button closeIconButton;
    @FXML
    private Button addLocationBtn;


    @FXML
    private void onAddLocationBtnClick(ActionEvent actionEvent) {

        tableViewLocation.getSelectionModel().select(null);
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(RoomsApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_LOCATION));
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
    private void onDeleteLocationBtnClick(ActionEvent actionEvent) {
        if (listModel.getSelectedLocation() != null) {

            // QuickAlert.showError("Möchten Sie dieses Standort sicher Löschen?");
            LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
            BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
            int myIndex = tableViewLocation.getSelectionModel().getSelectedIndex();

            Location locationToDelete = tableViewLocation.getSelectionModel().getSelectedItem();

            //Alert CONFIRMATION
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {

                try {
                    // check in DB how many bookings use the particular location
                    int bookingCountByLocation = bookingRepositoryJDBC.getBookingCountByProgramIdJoinLocationId(locationToDelete.getId());

                    if (bookingCountByLocation == 0) {

                        locationRepositoryJDBC.deleteLocation(locationToDelete);
                        listModel.locationList.remove(locationToDelete);
                    } else {
                        QuickAlert.showError("Diese Location wird für eine Buchung benötigt, Sie können nicht löschen! Bearbeiten Sie zuerst Ihre Buchungen!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            QuickAlert.showInfo("Bitte gewünschte Zeile markieren");
        }
    }


    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
        if (listModel.getSelectedLocation() != null) {
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(RoomsApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_LOCATION));
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

        assert addLocationBtn != null : "fx:id=\"addLocationBtn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert closeIconButton != null : "fx:id=\"closeIconButton\" was not injected: check your FXML file 'location-view.fxml'.";
        assert deleteLocationBtn != null : "fx:id=\"deleteLocationBtn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert locationBtnBar != null : "fx:id=\"locationBtnBar\" was not injected: check your FXML file 'location-view.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'location-view.fxml'.";
        assert settingsLocationBtn != null : "fx:id=\"settingsLocationBtn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert streetColumn != null : "fx:id=\"streetColumn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert tableViewLocation != null : "fx:id=\"tableViewLocation\" was not injected: check your FXML file 'location-view.fxml'.";
        assert zipColumn != null : "fx:id=\"zipColumn\" was not injected: check your FXML file 'location-view.fxml'.";

        closeIconButton.setVisible(false);
        UsabilityMethods.changeListener(searchBar, closeIconButton);

        authorityVisibility();
        initLocationTable();

        //LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        //locationRepo.getLocation();

        listModel.selectedLocationProperty().bind(tableViewLocation.getSelectionModel().selectedItemProperty());

    }

    public void initLocationTable() {
        tableViewLocation.setItems(listModel.sortedLocationList);
        listModel.sortedLocationList.comparatorProperty().bind(tableViewLocation.comparatorProperty());
        streetColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getStreet()));
        zipColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getZipcode()));
        cityColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCity()));
    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                break;
            case "coach":
                locationBtnBar.setVisible(false);
                break;
            default:
                locationBtnBar.setVisible(false);
                break;
        }
    }


    @FXML
    private void onSearchBarClick(KeyEvent actionEvent) {

        String searchTerm = searchBar.getText().toLowerCase(Locale.ROOT);

        listModel.filteredLocationList.setPredicate(location -> (location.getStreet().toLowerCase(Locale.ROOT).contains(searchTerm))
                || location.getZipcode().toLowerCase(Locale.ROOT).contains(searchTerm)
                || location.getCity().toLowerCase(Locale.ROOT).contains(searchTerm));
    }

    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }
}





























