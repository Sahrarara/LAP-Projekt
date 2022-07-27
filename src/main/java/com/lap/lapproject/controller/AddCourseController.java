package com.lap.lapproject.controller;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.ListModel;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Diese Klasse extends die Klasse BaseController es wird ein neuer Kurs erstellt oder upgedated, dabei werden die Daten entweder in die zuständigen Datenbank-klassen gesendet oder über diese abgefragt
 */
public class AddCourseController extends BaseController {
    CourseRepositoryJDBC courseRepo = new CourseRepositoryJDBC();
    ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
    BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
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
    private Label courseSizeNoticeLable;
    @FXML
    private Label checkDateNoticeLable;
    @FXML
    private Label courseUniqueNameNoticeLable;

    /**
     * Prüft den Zustand der Course -ChoiceBox, -Textfields und -DatePicker, wenn es bereits einen Kurs gibt dann werden die Felder befüllt
     */
    @FXML
    private void initialize() {
        assert courseChoiceBox != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseSizeNoticeLable != null : "fx:id=\"courseSizeNoticeLable\" was not injected: check your FXML file 'events-view.fxml'.";
        assert checkDateNoticeLable != null : "fx:id=\"checkDateNoticeLable\" was not injected: check your FXML file 'events-view.fxml'.";
        assert courseUniqueNameNoticeLable != null : "fx:id=\"courseUniqueNameNoticeLable\" was not injected: check your FXML file 'events-view.fxml'.";
        System.out.println("AddCourseController:: initialize");

        checkDateNoticeLable.setVisible(false);
        courseSizeNoticeLable.setVisible(false);
        courseUniqueNameNoticeLable.setVisible(false);
        UsabilityMethods.changeListenerForNumber(groupSizeTextField, courseSizeNoticeLable);//!
        UsabilityMethods.changeListenerInputText(courseNameTextField, courseUniqueNameNoticeLable);
        UsabilityMethods.changeListenerDataPicker(courseStartDatePicker, checkDateNoticeLable);
        UsabilityMethods.changeListenerDataPicker(courseEndDatePicker, checkDateNoticeLable);


        //Liste der ChoiceBox hinzufügen
        ObservableList<String> programNames = FXCollections.observableArrayList(
                listModel.programList.stream()
                        .map(program -> program.getProgramName())
                        .collect(Collectors.toList()));

        courseChoiceBox.setItems(programNames);

        //Update logik
        if (listModel.getSelectedCourse() != null) {
            String newCourseName = courseNameTextField.getText();
            String selectedCourseName = listModel.getSelectedCourse().getCourseName();
            courseNameTextField.setText(listModel.getSelectedCourse().getCourseName());
            courseChoiceBox.setValue(listModel.getSelectedCourse().getProgram().getProgramName());
            courseStartDatePicker.setValue(listModel.getSelectedCourse().getCourseStart());
            courseEndDatePicker.setValue(listModel.getSelectedCourse().getCourseEnd());
            groupSizeTextField.setText(String.valueOf(listModel.getSelectedCourse().getGroupSize()));
        }
    }

    /**
     * Schließt die AddCourse-Anwendung wieder
     *
     * @param actionEvent
     */
    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    /**
     * Beim ausführen diese Aktion wird der Kursname, Art des Programms bzw. Veranstaltung Kursdatum und Gruppengröße hinzugefügt
     * dabei wird geprüft ob der Kursname bereits existiert, ob das Datum der korrekten Form entspricht und Kursbeginn nicht nach Kursende steht und sich somit nicht überschneiden.
     * Die Felder dürfen nicht leer sein
     *
     * @param actionEvent
     * @throws SQLException - wenn nicht alle Felder ausgefüllt sind
     */
    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {

        System.out.println("AddCourseController:: onAddBtnClick");
        String courseName = courseNameTextField.getText();


        // Datum validieren
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String courseStartDateAsText = courseStartDatePicker.getEditor().getText().strip().replaceAll("-", ".");
        String courseEndDateAsText = courseEndDatePicker.getEditor().getText().strip().replaceAll("-", ".");


        if (!UsabilityMethods.isDDMMYYYYDate(courseStartDateAsText) || !UsabilityMethods.isDDMMYYYYDate(courseEndDateAsText)) {
            UsabilityMethods.addMessage(checkDateNoticeLable, "Bitte das Datum im dd.mm.yyyy Format angeben!");
            return;
        }
        LocalDate courseStart = LocalDate.parse(courseStartDateAsText, formatter);
        LocalDate courseEnd = LocalDate.parse(courseEndDateAsText, formatter);

        LocalDate today = LocalDate.now();


        if (courseEnd.compareTo(courseStart) < 0) {
            UsabilityMethods.addMessage(checkDateNoticeLable, "Kursbeginn ist nach dem Kursende!");
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


        if (!courseNameTextField.getText().isBlank()
                && !courseChoiceBox.getValue().toString().isBlank()
                && !(courseStart == null) && !(courseEnd == null)
                && !groupSizeTextField.getText().isBlank()) {
            int groupSize = Integer.parseInt(groupSizeTextField.getText());

            Course course = new Course(
                    courseName,
                    new Program(programId, programName),
                    courseStart.atStartOfDay().toLocalDate(),
                    courseEnd.atStartOfDay().toLocalDate(),
                    groupSize);

            int courseUniqueNameCount;
            courseUniqueNameCount = courseRepo.getCourseCountByCourseName(courseNameTextField.getText());
            if (listModel.getSelectedCourse() == null) {
                if (courseStart.isBefore(today)) {
                    UsabilityMethods.addMessage(checkDateNoticeLable, "Kursbeginn ist in der Vergangenheit!");
                    return;
                }

                if (courseUniqueNameCount == 0) { // check if this course is already in DB
                    try {
                        courseRepo.addCourse(course);
                        listModel.courseList.add(course);
                        listModel.bookingList.setAll(bookingRepositoryJDBC.readAll());//update booking
                        courseRepo.readAll();
                        moveToCoursePage();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    UsabilityMethods.addMessage(courseUniqueNameNoticeLable, "So ein Veranstaltungsname existiert schon in DB!");
                }

            } else {
                //Update logik
                course = listModel.getSelectedCourse();
                //if
                String newCourseName = courseNameTextField.getText();
                String selectedCourseName = listModel.getSelectedCourse().getCourseName();

                if (!newCourseName.equals(selectedCourseName)) {
                    if (courseRepo.getCourseCountByCourseName(newCourseName) == 0) {
                        updateCourse();
                    } else {
                        UsabilityMethods.addMessage(courseUniqueNameNoticeLable, "So ein Veranstaltungsname existiert schon in DB!");
                    }
                } else {
                    updateCourse();
                }
            }
        } else {
            QuickAlert.showError("Bitte alle Felder ausfüllen");
        }
    }

    /**
     * Der aktuelle Status des Fensters wird erfasst, hier wird durch das groupSizeTextField erkannt welches Fenster gemeint ist
     *
     * @return
     */
    private Stage getCurrentStage() {
        return (Stage) groupSizeTextField.getScene().getWindow();
    }

    /**
     * Schließt das aktuelle Fenster
     */
    private void moveToCoursePage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

    /**
     * Updated alle Felder und fügt die geänderten Einträge in der DatenBank ein.
     * Am Ende wird die Anwendung mit moveToCoursePage() wieder geschlossen.
     *
     * @throws SQLException
     */
    private void updateCourse() throws SQLException {
        LocalDate courseStart = LocalDate.parse(courseStartDatePicker.getEditor().getText().strip().replaceAll("-", "."), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate courseEnd = LocalDate.parse(courseEndDatePicker.getEditor().getText().strip().replaceAll("-", "."), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        listModel.getSelectedCourse().setCourseName(courseNameTextField.getText());
        listModel.getSelectedCourse().setCourseStart(courseStart);
        listModel.getSelectedCourse().setCourseEnd(courseEnd);
        listModel.getSelectedCourse().setProgram(programRepositoryJDBC.getProgramByProgramName((String) courseChoiceBox.getValue()));
        listModel.getSelectedCourse().setGroupSize(Integer.parseInt(groupSizeTextField.getText()));
        courseRepo.updateCourse(listModel.getSelectedCourse());
        listModel.courseList.set(listModel.courseList.indexOf(listModel.getSelectedCourse()), listModel.getSelectedCourse());
        moveToCoursePage();

        listModel.bookingList.setAll(bookingRepositoryJDBC.readAll()); // TODO: put later to ListModel
    }
}

