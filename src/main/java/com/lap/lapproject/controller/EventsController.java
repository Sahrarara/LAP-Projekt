package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

import java.io.IOException;

public class EventsController {
    @FXML
    private ButtonBar coursesBtnBar;

    @FXML
    private void onAddCourseBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_COURSE));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onDeleteCourseBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void initialize(){
        authorityVisibility();
    }

    private void authorityVisibility(){
        String authority = UserData.authority;
        switch (authority){
            case "admin":
                break;
            case "coach":
                break;
            default:
                coursesBtnBar.setVisible(false);
                break;
        }
    }
}