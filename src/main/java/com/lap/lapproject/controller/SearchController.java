package com.lap.lapproject.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.repos.LocationRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.model.ListModel.*;

public class SearchController {

    @FXML
    private ChoiceBox<?> dateSearchChoiceBox;

    @FXML
    private ChoiceBox<?> endTimeSearchChoiceBox;

    @FXML
    private ChoiceBox<?> startTimeSearchChoiceBox;


    public SearchController() {
    }

    @FXML
    void initialize() {
        assert dateSearchChoiceBox != null : "fx:id=\"dateSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert endTimeSearchChoiceBox != null : "fx:id=\"endTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";

        assert startTimeSearchChoiceBox != null : "fx:id=\"startTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";


        // get all existing programs from DB
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        UserRepositoryJDBC userRepo = new UserRepositoryJDBC();
        LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        RoomRepositoryJDBC roomNumberRepo = new RoomRepositoryJDBC();
        //programRepositoryJDBC.getAllPrograms();

    }
}
