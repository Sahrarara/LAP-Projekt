package com.lap.lapproject.controller;

import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddProgramController extends BaseController {
    ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
    CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();

    @FXML
    private TextField programNameTextField;
    @FXML
    private Label programNameNoticeLabel;

    @FXML
    private void initialize() throws SQLException {
        //Update logik
        if (listModel.getSelectedProgram() != null) {
            programNameTextField.setText(listModel.getSelectedProgram().getProgramName());
        }

        programNameNoticeLabel.setVisible(false);
        UsabilityMethods.changeListenerInputText(programNameTextField, programNameNoticeLabel);

    }

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        Program program = new Program(programNameTextField.getText());

        if (!programNameTextField.getText().isBlank()) {
            if ((listModel.getSelectedProgram() == null)){
                //Add logik
                System.out.println("AddProgrammController:: onAddBtnClick");
                int programUniqueNameCount = programRepositoryJDBC.getProgramsCountByProgramName(programNameTextField.getText());
                if (programUniqueNameCount == 0) {
                    try {
                    // check if this Program exists in DM
                        programRepositoryJDBC.addProgram(program);  //Program is added to a list. programview list
                        listModel.programList.add(program);
                        programRepositoryJDBC.readProgram();
                        moveToProgramPage();

                        listModel.courseList.setAll(courseRepositoryJDBC.readAll());//TODO: put later to ListModel

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    UsabilityMethods.addMessage(programNameNoticeLabel, "Dieser Programmname existiert schon in DB!");
                }
            } else {
                //Update
                String newProgramName = programNameTextField.getText();
                String selectedProgramName = listModel.getSelectedProgram().getProgramName();

                if (!newProgramName.equals(selectedProgramName)) {
                    if ( programRepositoryJDBC.getProgramsCountByProgramName(newProgramName) == 0) {
                        updateProgram();
                    }else {
                        UsabilityMethods.addMessage(programNameNoticeLabel, "Dieser Programmname existiert schon in DB!");
                    }
                }else {
                    updateProgram();
                }
            }
        } else {
            QuickAlert.showError("Bitte Programmnamen angeben");
        }
    }

    private Stage getCurrentStage() {
        return (Stage) programNameTextField.getScene().getWindow();
    }

    private void moveToProgramPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

    private  void updateProgram() throws SQLException {
        listModel.getSelectedProgram().setProgramName(programNameTextField.getText());
        programRepositoryJDBC.updateProgram(listModel.getSelectedProgram());
        listModel.programList.set(listModel.programList.indexOf(listModel.getSelectedProgram()), listModel.getSelectedProgram());
        moveToProgramPage();

        listModel.courseList.setAll(courseRepositoryJDBC.readAll());//TODO: put later to ListModel
    }
}