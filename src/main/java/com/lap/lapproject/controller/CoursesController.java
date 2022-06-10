package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Course;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import java.util.Locale;

public class CoursesController extends BaseController {
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
    private ChoiceBox filterChoiceBox;
    @FXML
    private TextField searchBar;
    @FXML
    private Button closeIconButton;

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

        Course courseToDelete = tableViewEvent.getSelectionModel().getSelectedItem();
        //Alert CONFIRMATION
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {

            try {
                courseRepositoryJDBC.deleteCourse(courseToDelete);
                listModel.courseList.remove(courseToDelete);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
        System.out.println("DEBUG: " + tableViewEvent.getSelectionModel().selectedItemProperty());
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

        closeIconButton.setVisible(false);
        UsabilityMethods.changeListener(searchBar, closeIconButton);

        authorityVisibility();
        //CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
        initEventTable();

        listModel.selectedCourseProperty().bind(tableViewEvent.getSelectionModel().selectedItemProperty());
    }

    private void initEventTable() {
        tableViewEvent.setItems(listModel.filteredCourseList);
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



    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {
        String searchTerm = searchBar.getText();
        ObservableList<Course> filteredList = FXCollections.observableArrayList();
        if (searchTerm.equals("")) {
            filteredList = listModel.courseList;
        } else {
            for (Course elem : listModel.courseList) {
                if (
                        elem.getCourseName().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getProgram().getProgramName().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getCourseStart().toString().contains(searchTerm)
                                || elem.getCourseEnd().toString().contains(searchTerm)
                                || String.valueOf(elem.getGroupSize()).contains(searchTerm)
                ) {
                    filteredList.add(elem);
                }
            }
        }
        tableViewEvent.setItems(filteredList);
    }


    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }
}
















