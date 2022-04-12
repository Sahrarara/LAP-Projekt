package com.lap.lapproject.controller;

import com.lap.lapproject.application.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SidebarController extends BaseController{

    private static final Logger log = LoggerFactory.getLogger(SidebarController.class);
    @FXML
    private Button trainerBtn;
    @FXML
    private Button logoutBtn;

    @FXML
    void onCoreDataButtonPressed(ActionEvent event){
        log.debug("Button pressed");
        model.setPathForDetailView(Constants.PATH_TO_FXML_DASHBOARD);
    }

    @FXML
    private void onTrainerBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_TRAINER);
    }

    @FXML
    private void onLogoutBtnClick(ActionEvent actionEvent) {
        //TODO: clear entire Stage (Sidebar needs to be removed)
        model.setPathForDetailView(Constants.PATH_TO_FXML_LOGIN);
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
}
