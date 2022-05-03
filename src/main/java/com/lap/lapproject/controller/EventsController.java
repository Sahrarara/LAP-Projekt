package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.ListModel;
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

public class EventsController {
    @FXML
    private ButtonBar coursesBtnBar;


    @FXML
    private TableView<Course> tableViewEvent;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> courseStartColumn;
    @FXML
    private TableColumn<Course, String> courseEndColumn;
    @FXML
    private TableColumn<Course, String> programColumn;
    @FXML
    private TableColumn<Course, String> groupSizeColumn;

    @FXML
    private void onAddCourseBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_COURSE));
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
    private void onDeleteCourseBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void initialize() {
        assert courseEndColumn != null : "fx:id=\"courseEndColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseNameColumn != null : "fx:id=\"courseNameColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseStartColumn != null : "fx:id=\"courseStartColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert coursesBtnBar != null : "fx:id=\"coursesBtnBar\" was not injected: check your FXML file 'events-view.fxml'.";
        assert groupSizeColumn != null : "fx:id=\"groupSizeColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert programColumn != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert tableViewEvent != null : "fx:id=\"tableViewEvent\" was not injected: check your FXML file 'events-view.fxml'.";

        authorityVisibility();
        initEventTable();
    }

    private void initEventTable() {
        tableViewEvent.setItems(ListModel.courseList);
        courseNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCourseName()));
        courseStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getProgram().getProgramName()));
    }

    private void authorityVisibility() {
        String authority = UserData.authority;
        switch (authority) {
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
