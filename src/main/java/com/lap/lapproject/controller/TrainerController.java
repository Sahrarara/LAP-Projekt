package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.model.UserData;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;


public class TrainerController extends BaseController{
    @FXML
    private ButtonBar trainerBtnBar;


    @FXML
    private TableView<Trainer> tableViewTrainer;
    @FXML
    private TableColumn<Trainer, String> firstNameColumn;
    @FXML
    private TableColumn<Trainer, String> lastNameColumn;
    @FXML
    private TableColumn<Trainer, String> emailColumn;
    @FXML
    private TableColumn<Trainer, String> phoneColumn;
    @FXML
    private TableColumn<Trainer, Boolean> activeStatusColumn;


    @FXML
    private void onAddTrainerBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_TRAINER));
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
    private void onDeleteTrainerBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }


    @FXML
    private void initialize() {
        assert tableViewTrainer != null : "fx:id=\"tableViewTrainer\" was not injected: check your FXML file 'trainer.fxml'.";
        assert emailColumn != null : "fx:id=\"emailColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert firstNameColumn != null : "fx:id=\"firstNameColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert lastNameColumn != null : "fx:id=\"lastNameColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert phoneColumn != null : "fx:id=\"phoneColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert activeStatusColumn != null : "fx:id=\"activeStatusColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert trainerBtnBar != null : "fx:id=\"trainerBtnBar\" was not injected: check your FXML file 'trainer.fxml'.";

        authorityVisibility();

        initTrainerTable();
    }

    private void initTrainerTable() {
        tableViewTrainer.setItems(listModel.trainerList);
        firstNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getfName()));
        lastNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getlName()));
        emailColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getEmail()));
        phoneColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getPhoneNmbr()));
        activeStatusColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getActiveStatus()));
    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                break;
            case "coach":
                trainerBtnBar.setVisible(false);
                break;
            default:
                trainerBtnBar.setVisible(false);
                break;
        }
    }
}
