package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.repos.LocationRepositoryJDBC;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class LocationController extends BaseController{
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
    private void onAddLocationBtnClick(ActionEvent actionEvent) {
        tableViewLocation.getSelectionModel().select(null);
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_LOCATION));
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
        LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
        int myIndex = tableViewLocation.getSelectionModel().getSelectedIndex();

        Location location1 = tableViewLocation.getSelectionModel().getSelectedItem();


        try {
            locationRepositoryJDBC.deleteLocation(location1);
            listModel.locationList.remove(location1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_LOCATION));
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
    private void initialize() {
        assert tableViewLocation != null : "fx:id=\"tableViewLocation\" was not injected: check your FXML file 'location-view.fxml'.";
        assert streetColumn != null : "fx:id=\"streetColumn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert zipColumn != null : "fx:id=\"zipColumn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert locationBtnBar != null : "fx:id=\"locationBtnBar\" was not injected: check your FXML file 'location-view.fxml'.";
        assert deleteLocationBtn != null : "fx:id=\"deleteLocationBtn\" was not injected: check your FXML file 'location-view.fxml'.";
        assert settingsLocationBtn != null : "fx:id=\"settingsLocationBtn\" was not injected: check your FXML file 'location-view.fxml'.";

        //authorityVisibility();
        //LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        ////locationRepo.getLocation();
        //initLocationTable();


        authorityVisibility();
        LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        //locationRepo.getLocation();
        initLocationTable();
         listModel.selectedLocationProperty().bind(tableViewLocation.getSelectionModel().selectedItemProperty());


        //TODO: add a Textfield ID and imageView ID in the fxml file for the searchbar and magnifying glass
        //TODO: write the UsabilityMethod.changeListener method in here with the IDs of the searchbar and magnifying glass (you can look it up in ProgramController)
    }

    public void initLocationTable() {
        tableViewLocation.setItems(listModel.locationList);
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



}
