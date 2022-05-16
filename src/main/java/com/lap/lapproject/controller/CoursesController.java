package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Course;
import javafx.beans.property.SimpleObjectProperty;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
public class CoursesController extends BaseController{
    @FXML
    private ButtonBar coursesBtnBar;

    @FXML
    private Button settingsCourseBtn;

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
    private TableColumn<Course, Integer> groupSizeColumn;

    @FXML
    private void onAddCourseBtnClick(ActionEvent actionEvent) {
        tableViewEvent.getSelectionModel().select(null);
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
        CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
        int myIndex = tableViewEvent.getSelectionModel().getSelectedIndex();

        Course course1 = tableViewEvent.getSelectionModel().getSelectedItem();


        try {
            courseRepositoryJDBC.deleteCourse(course1);
            listModel.courseList.remove(course1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
        System.out.println("Pressed Course settings");
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_COURSE));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        listModel.selectedCourseProperty().bind(tableViewEvent.getSelectionModel().selectedItemProperty());

        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void initialize() {
        assert tableViewEvent != null : "fx:id=\"tableViewEvent\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseNameColumn != null : "fx:id=\"courseNameColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseStartColumn != null : "fx:id=\"courseStartColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseEndColumn != null : "fx:id=\"courseEndColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert programColumn != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert groupSizeColumn != null : "fx:id=\"groupSizeColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert coursesBtnBar != null : "fx:id=\"coursesBtnBar\" was not injected: check your FXML file 'events-view.fxml'.";

        authorityVisibility();
        //CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
        initEventTable();

        listModel.selectedCourseProperty().bind(tableViewEvent.getSelectionModel().selectedItemProperty());

    }

    private void initEventTable() {
        tableViewEvent.setItems(listModel.courseList);
        courseNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCourseName()));
        courseStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCourseStart().toString().substring(0, 10)));
        courseEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCourseEnd().toString().substring(0, 10)));
        programColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getProgram().getProgramName()));
        groupSizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getGroupSize()));
    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
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
