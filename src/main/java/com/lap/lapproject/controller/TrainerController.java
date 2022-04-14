package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static com.lap.lapproject.controller.BaseController.model;

public class TrainerController {

    @FXML
    private Button addTrainerBtn;

    @FXML
    private void onAddTrainerBtnClick(ActionEvent actionEvent) {
        openNewWindow();
        //model.setPathForDetailView(Constants.PATH_TO_FXML_CREATE_NEW_TRAINER);
    }

    @FXML
    private void onDeleteTrainerBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }


    private Stage getCurrentStage(){
        return (Stage) addTrainerBtn.getScene().getWindow();
    }

    private void openNewWindow(){
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_TRAINER));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        stage.setTitle("Trainer hinzufügen");
        stage.setScene(scene);
        stage.show();
    }

}
