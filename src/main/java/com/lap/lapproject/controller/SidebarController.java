package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Admin;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(SidebarController.class);
    @FXML
    private Button trainerBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label nameLabel;

    @FXML
    void onCoreDataButtonPressed(ActionEvent event){
        model.setPathForDetailView(Constants.PATH_TO_FXML_DASHBOARD);
    }

    @FXML
    private void onTrainerBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_TRAINER);
    }

    @FXML
    private void onLogoutBtnClick(ActionEvent actionEvent) {
        moveToLogin();
    }

    @FXML
    private void onUserBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_PROFILE);
    }

    @FXML
    private void onRoomBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_ROOM);
    }

    @FXML
    private void onGroupBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_GROUP);
    }

    @FXML
    private void onLocationBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_LOCATION);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText("Gast");
    }

    private Stage getCurrentStage(){
        return (Stage) nameLabel.getScene().getWindow();
    }

    private void moveToLogin(){
        Stage currentStage = this.getCurrentStage();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_LOGIN));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        currentStage.setTitle("Raum Management");
        currentStage.setScene(scene);
        currentStage.show();
    }

}
