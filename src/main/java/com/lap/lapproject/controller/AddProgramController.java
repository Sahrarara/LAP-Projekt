package com.lap.lapproject.controller;

import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        ProgramRepositoryJDBC programRepo = new ProgramRepositoryJDBC();
        Program program = new Program(programNameTextField.getText());
        programRepo.addProgram(program);
        getCurrentStage().close();
    }

    private Stage getCurrentStage(){
        return (Stage) programNameTextField.getScene().getWindow();
    }

}
