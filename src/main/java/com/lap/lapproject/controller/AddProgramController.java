package com.lap.lapproject.controller;

import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddProgramController extends BaseController {
    @FXML
    private TextField programNameTextField;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        Program program = new Program(programNameTextField.getText());
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();


        if (!programNameTextField.getText().isBlank()) {     //method is only used if textfield is not empty

            if (listModel.getSelectedProgram() == null) {
                //Add logik
                System.out.println("AddProgrammController:: onAddBtnClick");
                try {
                   programRepositoryJDBC.addProgram(program);  //Program is added to a list. programview list
                    // is emptied
                    //programRepositoryJDBC.readProgram();         //Programlist in programview is generated from
                    // scratch
                    listModel.programList.add(program);


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                moveToProgramPage();
            } else {
                //Update logik
                program = listModel.getSelectedProgram();
                program.setProgramName(programNameTextField.getText());
                programRepositoryJDBC.updateProgram(program);
                listModel.programList.set(listModel.programList.indexOf(program), program);
                moveToProgramPage();
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


    @FXML
    private void initialize() {
        //Update logik
        if (listModel.getSelectedProgram() != null) {
            programNameTextField.setText(listModel.getSelectedProgram().getProgramName());
        }
    }

}
