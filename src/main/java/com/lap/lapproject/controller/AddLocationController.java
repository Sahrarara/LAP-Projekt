package com.lap.lapproject.controller;

import com.lap.lapproject.model.Location;
import com.lap.lapproject.repos.LocationRepositoryJDBC;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddLocationController {
    @FXML
    private TextField streetNameTextField;
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextField locationTextField;

    @FXML
    private void initialize(){
        System.out.println("AddLocationController:: initialize");

    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {
        LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();

        System.out.println("AddLocationController:: onAddBtnClick");
        String street = streetNameTextField.getText();
        String zip = zipCodeTextField.getText();
        String city = locationTextField.getText();


        Location location = new Location(street, zip, city);
        // TODO check duplicate
        LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
        try {
            locationRepositoryJDBC.addLocation(location);
            locationRepo.getLocation();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        moveToLocationPage();


    }

    private Stage getCurrentStage(){
        System.out.println("AddLocationController:: getCurrentStage");
        return (Stage) locationTextField.getScene().getWindow();
    }

    private void moveToLocationPage(){
        Stage currentStage = this.getCurrentStage();


        currentStage.close();

/*
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_LOCATION ));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        currentStage.setTitle("Raum Management");
        currentStage.setScene(scene);
        currentStage.show();

 */

    }


}
