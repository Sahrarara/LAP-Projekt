package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SidebarController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(SidebarController.class);
    @FXML
    private Button logoutBtn;
    @FXML
    private Label nameLabel;
    @FXML
    private Button locationBtn;
    @FXML
    private Button profileBtn;
    @FXML
    private Button roomsBtn;
    @FXML
    private Button courseBtn;
    @FXML
    private Button trainerBtn;
    @FXML
    private Button calenderBtn;
    @FXML
    private ImageView profileIcon;
    @FXML
    private ImageView locationIcon;
    @FXML
    private ImageView roomsIcon;
    @FXML
    private ImageView courseIcon;
    @FXML
    private ImageView trainerIcon;

    @FXML
    void onDashboardButtonPressed(ActionEvent event) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_DASHBOARD);
    }

    @FXML
    private void onLocationBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_LOCATION);
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
    private void onCourseBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_COURSE);
    }

    @FXML
    private void onTrainerBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_TRAINER);
    }

    @FXML
    private void onCalenderBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_CALENDER);
    }

    @FXML
    private void onSearchBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_SEARCH);
    }

    @FXML
    private void onProgramBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_PROGRAM);
    }

    @FXML
    private void onEquipmentBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_EQUIPMENT);
    }

    @FXML
    private void onBookingBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_BOOKING);
    }

    @FXML
    private void onLogoutBtnClick(ActionEvent actionEvent) {
        UserData.firstName = null;
        UserData.lastName = null;
        UserData.email = null;
        UserData.telephoneNmbr = "0";
        UserData.description = null;
        UserData.authority = "guest";
        moveToLogin();
    }

    @FXML
    private void initialize() {
        authorityVisibility();
        setUsername();
    }

    private void authorityVisibility() {
        String authority = UserData.authority;
        switch (authority) {
            case "admin":
                System.out.println("Admin privileges");
                break;
            case "coach":
                System.out.println("Coach privileges");
                break;
            default:
                System.out.println("Guest privileges");
                logoutBtn.setText(" Zurück zum Login");
                profileIcon.setVisible(false);
                profileBtn.setVisible(false);
                break;
        }
    }

    private void setUsername() {
        if (UserData.firstName != null) {
            nameLabel.setText(UserData.firstName);
        } else {
            nameLabel.setText("Gast");
        }
    }

    private Stage getCurrentStage() {
        return (Stage) nameLabel.getScene().getWindow();
    }

    private void moveToLogin() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_LOGIN));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentStage.setTitle("Raum Management");
        currentStage.setScene(scene);
        currentStage.show();
    }


}
