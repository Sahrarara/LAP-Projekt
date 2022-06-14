package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static com.lap.lapproject.controller.ProfileController.imageFromBytes;

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
    private Circle bannerImg;

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
       // listModel.ListModel();
        model.setPathForDetailView(Constants.PATH_TO_FXML_PROFILE);
    }

    @FXML
    private void onRoomBtnClick(ActionEvent actionEvent) {
        //listModel.ListModel();
        model.setPathForDetailView(Constants.PATH_TO_FXML_ROOM);
    }

    @FXML
    private void onCourseBtnClick(ActionEvent actionEvent) {
        //CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
        //listModel.courseList.setAll(courseRepositoryJDBC.readAll());
        //listModel.ListModel();
        model.setPathForDetailView(Constants.PATH_TO_FXML_COURSE);
    }

    @FXML
    private void onTrainerBtnClick(ActionEvent actionEvent) {
        //listModel.ListModel();
        model.setPathForDetailView(Constants.PATH_TO_FXML_TRAINER);
    }

    @FXML
    private void onCalenderBtnClick(ActionEvent actionEvent) {
        model.setPathForDetailView(Constants.PATH_TO_FXML_CALENDER);
    }

    @FXML
    private void onSearchBtnClick(ActionEvent actionEvent) {
        //listModel.ListModel();
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
        //listModel.ListModel();
        model.setPathForDetailView(Constants.PATH_TO_FXML_BOOKING);
    }

    @FXML
    private void onLogoutBtnClick(ActionEvent actionEvent) {
        model.setLoggedInUser(null);
        moveToLogin();
    }

    @FXML
    private void initialize() throws IOException {
        assert bannerImg != null : "fx:id=\"bannerImg\" was not injected: check your FXML file 'sidebar-view.fxml'.";
        authorityVisibility();
        setUsername();
    }

    private boolean authorityVisibility() throws IOException {
        BufferedImage bImage = ImageIO.read(new FileInputStream("src/main/resources/com/lap/lapproject/images/lapproject/images/user.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] imageNoVisible = bos.toByteArray();
        String authority = model.getAuthority();

        switch (authority) {
            case "admin":
                System.out.println("Admin privileges");
                if(model.getLoggedInUser().getPhoto() != null) {
                    bannerImg.setFill(new ImagePattern(imageFromBytes(model.getLoggedInUser().getPhoto())));
                } else {
                    bannerImg.setFill(new ImagePattern(imageFromBytes(imageNoVisible)));
                }
                break;
            case "coach":
                System.out.println("Coach privileges");

                if(model.getLoggedInUser().getPhoto() != null) {
                    bannerImg.setFill(new ImagePattern(imageFromBytes(model.getLoggedInUser().getPhoto())));
                } else {
                    bannerImg.setFill(new ImagePattern(imageFromBytes(imageNoVisible)));
                }
                break;
            default:
                System.out.println("Guest privileges");
                logoutBtn.setText(" Zurück zum Login");
                profileIcon.setVisible(false);
                profileBtn.setVisible(false);
                bannerImg.setFill(new ImagePattern(imageFromBytes(imageNoVisible)));
                break;
        }
        return false;
    }


    private void setUsername() {
        if (model.getLoggedInUser() != null) {
            nameLabel.setText(model.getLoggedInUser().getfName());
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
