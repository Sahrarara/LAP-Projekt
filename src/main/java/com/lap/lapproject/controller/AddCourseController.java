package com.lap.lapproject.controller;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.lap.lapproject.model.ListModel.programList;

public class AddCourseController {
    @FXML
    private TextField courseNameTextField;
    @FXML
    private ChoiceBox courseChoiceBox;
    @FXML
    private DatePicker courseStartDatePicker;
    @FXML
    private DatePicker courseEndDatePicker;
    @FXML
    private TextField groupSizeTextField;


    @FXML
    private void initialize(){
        assert courseChoiceBox != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        System.out.println("AddCourseController:: initialize");
        //ObservableList<Location> locations = FXCollections.observableArrayList();
        //ObservableList<Program> programs = FXCollections.observableArrayList();

        // get all existing programs from DB
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        programRepositoryJDBC.getAllPrograms();

        //Liste der ChoiceBox hinzufügen
        ObservableList<String> programNames = FXCollections.observableArrayList(
                programList.stream()
                        .map(program -> program.getProgramName())
                        .collect(Collectors.toList()));

        courseChoiceBox.setItems(programNames);

    }



    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) {

        CourseRepositoryJDBC courseRepo= new CourseRepositoryJDBC();
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();

        System.out.println("AddCourseController:: onAddBtnClick");
        String courseName = courseNameTextField.getText();
        String programName = courseChoiceBox.getValue().toString();
        int programId = programRepositoryJDBC.getProgramIdByProgramName(programName);
        LocalDate courseStart = courseStartDatePicker.getValue();
        LocalDate courseEnd = courseEndDatePicker.getValue();
        //int groupSize = groupSizeTextField.getPrefColumnCount();
        int groupSize = Integer.parseInt(groupSizeTextField.getText());

        Course course = new Course(
                courseName,
                new Program(programId, programName),
                courseStart.atStartOfDay(),
                courseEnd.atStartOfDay(),
                groupSize);

        // TODO check duplicate
        CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
        try {
            courseRepositoryJDBC.addCourse(course);
            courseRepo.getCourse();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        moveToCoursePage();



       /* if (!courseName.isBlank() && !programName.isBlank() && !(courseStart == null) && !(courseEnd == null) && !groupSizeTextField.getText().isBlank()){
            //TODO: Insert create new Course function here
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }*/
    }

    private Stage getCurrentStage(){
        return (Stage) groupSizeTextField.getScene().getWindow();
    }

    private void moveToCoursePage(){
        Stage currentStage = this.getCurrentStage();


        currentStage.close();
    }
}

