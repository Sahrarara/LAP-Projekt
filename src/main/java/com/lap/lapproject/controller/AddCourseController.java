package com.lap.lapproject.controller;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
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
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


public class AddCourseController extends BaseController {
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
    private void initialize() {
        assert courseChoiceBox != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        System.out.println("AddCourseController:: initialize");

        //Liste der ChoiceBox hinzufügen
        ObservableList<String> programNames = FXCollections.observableArrayList(
                listModel.programList.stream()
                        .map(program -> program.getProgramName())
                        .collect(Collectors.toList()));

        courseChoiceBox.setItems(programNames);
        //courseChoiceBox.setItems(listModel.programList);//empfelung vom Hr.Öller statt: courseChoiceBox.setItems(programNames);

        //Update logik
        if (listModel.getSelectedCourse() != null) {
            courseNameTextField.setText(listModel.getSelectedCourse().getCourseName());
            courseChoiceBox.setValue(listModel.getSelectedCourse().getProgram().getProgramName());
            courseStartDatePicker.setValue(listModel.getSelectedCourse().getCourseStart());
            courseEndDatePicker.setValue(listModel.getSelectedCourse().getCourseEnd());
            groupSizeTextField.setText(String.valueOf(listModel.getSelectedCourse().getGroupSize()));
        }
    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        CourseRepositoryJDBC courseRepo = new CourseRepositoryJDBC();
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();

        System.out.println("AddCourseController:: onAddBtnClick");
        String courseName = courseNameTextField.getText();


        // Datum validieren
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String courseStartDateAsText = courseStartDatePicker.getEditor().getText().strip().replaceAll("-", ".");
        String courseEndDateAsText = courseEndDatePicker.getEditor().getText().strip().replaceAll("-", ".");

        if (courseStartDateAsText.isEmpty() || courseEndDateAsText.isEmpty()) {
            QuickAlert.showError("Bitte alle Datum Felder ausfüllen!");
            return;
        }

        if (!UsabilityMethods.isDDMMYYYYDate(courseStartDateAsText) || !UsabilityMethods.isDDMMYYYYDate(courseEndDateAsText)) {
            QuickAlert.showError("Bitte geben Datum in dd.mm.yyyy Format");
            return;
        }

        LocalDate courseStart = LocalDate.parse(courseStartDateAsText, formatter);
        LocalDate courseEnd = LocalDate.parse(courseEndDateAsText, formatter);
        LocalDate today = LocalDate.now();

        if (courseEnd.compareTo(courseStart) < 0 || courseStart.isBefore(today)) {
            QuickAlert.showError("Kursbegin ist nach dem Kursend oder in der Vergangenheit. Bitte Datum überprüfen!");
            return;
        }



        if (courseChoiceBox.getValue() == null) {
            QuickAlert.showError("Bitte Programm wählen!");
            return;
        }

        String programName = courseChoiceBox.getValue().toString();
        Program program = programRepositoryJDBC.getProgramByProgramName(programName);
        if (program == null) {
            QuickAlert.showError("Program wurde nicht gefunden");
        }
        int programId = program.getId();

        int groupSize = Integer.parseInt(groupSizeTextField.getText());

        Course course = new Course(
                courseName,
                new Program(programId, programName),
                courseStart.atStartOfDay().toLocalDate(),
                courseEnd.atStartOfDay().toLocalDate(),
                groupSize);
       if (!courseNameTextField.getText().isBlank() && !courseChoiceBox.getValue().toString().isBlank() && !(courseStart == null) && !(courseEnd == null) && !groupSizeTextField.getText().isBlank()) {
           if (listModel.getSelectedCourse() == null) {
               System.out.println("AddCourseController:: onAddBtnClick");

               try {

            courseRepo.addCourse(course);
            listModel.courseList.add(course);

            listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());//update booking
            courseRepo.readAll();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
                   moveToCoursePage();
               } else {
                   //Update logik
                   course = listModel.getSelectedCourse();
                   course.setCourseName(courseNameTextField.getText());
                   //course.setCourseStart(courseStartDatePicker.getValue());
               //course.setCourseEnd(courseEndDatePicker.getValue());
                   course.setCourseStart(courseStart);
                   course.setCourseEnd(courseEnd);
                   course.setProgram(programRepositoryJDBC.getProgramByProgramName((String) courseChoiceBox.getValue()));
                   //course.setProgram((Program) courseChoiceBox.getValue()); //--empfelung vom Hr.Öller statt zeile oben
                   course.setGroupSize(Integer.parseInt(groupSizeTextField.getText()));
                   courseRepo.updateCourse(course);
                   listModel.courseList.set(listModel.courseList.indexOf(course), course);

                   listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());
                   moveToCoursePage();
               }
           } else {
               QuickAlert.showError("Bitte alle Felder ausfüllen");
           }
    }
          // TODO check duplicate


    private Stage getCurrentStage() {
        return (Stage) groupSizeTextField.getScene().getWindow();
    }

    private void moveToCoursePage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }
}

