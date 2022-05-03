package com.lap.lapproject.controller;

import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddProgramController {
    @FXML
    private TextField programNameTextField;

    @FXML
    private void onAbortBtnClick(ActionEvent actionEvent) {
        getCurrentStage().close();
    }

    @FXML
    private void onAddBtnClick(ActionEvent actionEvent) throws SQLException {
        if (!programNameTextField.getText().isBlank()){     //method is only used if textfield is not empty
            System.out.println("AddProgrammController:: onAddBtnClick");
            Program program = new Program(programNameTextField.getText());
            ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
            try {
                programRepositoryJDBC.addProgram(program);  //Program is added to a list. programview list is emptied
                programRepositoryJDBC.getProgram();         //Programlist in programview is generated from scratch
            } catch (SQLException e) {
                e.printStackTrace();
            }
            moveToProgramPage();
        } else {
            QuickAlert.showError("Bitte Programmnamen angeben");
        }
    }

    private Stage getCurrentStage(){
        return (Stage) programNameTextField.getScene().getWindow();
    }

    private void moveToProgramPage() {
        Stage currentStage = this.getCurrentStage();
        currentStage.close();
    }

}
