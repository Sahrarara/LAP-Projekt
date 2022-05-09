package com.lap.lapproject.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lap.lapproject.repos.LocationRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import com.lap.lapproject.repos.RoomRepositoryJDBC;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import static com.lap.lapproject.model.ListModel.*;

public class SearchController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> dateSearchChoiceBox;

    @FXML
    private ChoiceBox<?> endTimeSearchChoiceBox;

    @FXML
    private ChoiceBox<?> equipmentSearchChoiceBox;

    @FXML
    private ChoiceBox erstellerSearchChoiceBox;

    @FXML
    private ChoiceBox<?> eveliableSearchChoiceBox;

    @FXML
    private ChoiceBox<String> locationSearchChoiceBox;

    @FXML
    private ChoiceBox programNameChoiceBox;

    @FXML
    private ChoiceBox roomSearchChoiceBox;

    @FXML
    private ChoiceBox roomSizeSearchChoiceBox;

    @FXML
    private ChoiceBox<?> startTimeSearchChoiceBox;

    @FXML
    private ChoiceBox vortragenderSearchChoiceBox;


    public SearchController() {
    }

    @FXML
    void initialize() {
        assert dateSearchChoiceBox != null : "fx:id=\"dateSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert endTimeSearchChoiceBox != null : "fx:id=\"endTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert equipmentSearchChoiceBox != null : "fx:id=\"equipmentSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert erstellerSearchChoiceBox != null : "fx:id=\"erstellerSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert eveliableSearchChoiceBox != null : "fx:id=\"eveliableSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert locationSearchChoiceBox != null : "fx:id=\"locationSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert programNameChoiceBox != null : "fx:id=\"programNameChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert roomSearchChoiceBox != null : "fx:id=\"roomSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert roomSizeSearchChoiceBox != null : "fx:id=\"roomSizeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert startTimeSearchChoiceBox != null : "fx:id=\"startTimeSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";
        assert vortragenderSearchChoiceBox != null : "fx:id=\"vortragenderSearchChoiceBox\" was not injected: check your FXML file 'search-view.fxml'.";


       /* // get all existing programs from DB
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        programRepositoryJDBC.getAllPrograms();

        //--------------------- choicebox programNames------------------
        ObservableList<String> programNames = FXCollections.observableArrayList(
                programList.stream()
                        .map(program -> program.getProgramName())
                        .collect(Collectors.toList()));

        programNameChoiceBox.setItems(programNames);


        //--------------------- choicebox vortragende------------------
        UserRepositoryJDBC userRepo = new UserRepositoryJDBC();
        userRepo.getTrainer();
        ObservableList<String> tainerName = FXCollections.observableArrayList(
                trainerList.stream()
                        .map(trainer -> trainer.getfName() + " " + trainer.getlName())
                        .collect(Collectors.toList()));

        vortragenderSearchChoiceBox.setItems(tainerName);



        //--------------------- choicebox location------------------
        LocationRepositoryJDBC locationRepo = new LocationRepositoryJDBC();
        locationRepo.getLocation();
        ObservableList<String> locationName = FXCollections.observableArrayList(
                locationList.stream()
                        .map(location -> location.getStreet())
                        .collect(Collectors.toList()));

        locationSearchChoiceBox.setItems(locationName);




        //--------------------- choicebox room------------------
        RoomRepositoryJDBC roomNumberRepo = new RoomRepositoryJDBC();
        roomNumberRepo.getRoom();
        ObservableList<String> roomNumber = FXCollections.observableArrayList(
                roomList.stream()
                        .map(room -> room.getRoomNumber())
                        .collect(Collectors.toList()));

        roomSearchChoiceBox.setItems(roomNumber);


        //--------------------- choicebox roomSize------------------
        RoomRepositoryJDBC roomSizeRepo = new RoomRepositoryJDBC();
        roomSizeRepo.getRoom();
        ObservableList<Integer> roomSize = FXCollections.observableArrayList(
                roomList.stream()
                        .map(room -> room.getSize())
                        .collect(Collectors.toList()));

        roomSizeSearchChoiceBox.setItems(roomSize);


       /* //--------------------- choicebox ersteller--------not finished!!!!----------
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
        //userRepositoryJDBC.getUser();
        ObservableList<String> erstellerTyp = FXCollections.observableArrayList(
                userList.stream()
                        .map(user -> user.getAuthority())
                        .collect(Collectors.toList()));

        erstellerSearchChoiceBox.setItems(erstellerTyp);*/
    }

}
