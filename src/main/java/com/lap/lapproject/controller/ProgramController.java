package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProgramController extends BaseController {
    @FXML
    private Button addProgramBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button settingsBtn;
    @FXML
    private ButtonBar programBtnBar;

    @FXML
    private TableView<Program> tableViewProgram;
    @FXML
    private TableColumn<Program, String> programColumn;

    @FXML
    private void onAddProgramBtnClick(ActionEvent actionEvent) {
        //setzt wert auf null, das der Program erkennt das Add Function aufgerufen wird
        tableViewProgram.getSelectionModel().select(null);
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_PROGRAM));
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
    private void onDeleteBtnClick(ActionEvent actionEvent) {
        ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
        int myIndex = tableViewProgram.getSelectionModel().getSelectedIndex();

        Program program1 = tableViewProgram.getSelectionModel().getSelectedItem();


        try {
            programRepositoryJDBC.deleteProgram(program1);
            listModel.programList.remove(program1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onEditBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_PROGRAM));
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
    private void initialize() throws SQLException {
        assert tableViewProgram != null : "fx:id=\"tableViewProgram\" was not injected: check your FXML file 'program-view.fxml'.";
        assert programColumn != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert addProgramBtn != null : "fx:id=\"addProgramBtn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert programBtnBar != null : "fx:id=\"programBtnBar\" was not injected: check your FXML file 'program-view.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'program-view.fxml'.";


        authorityVisibility();
        initTableProgram();
        //nimmt daten von tabele und befüllt das Formular
        listModel.selectedProgramProperty().bind(tableViewProgram.getSelectionModel().selectedItemProperty());

    }

    private void initTableProgram() {
        tableViewProgram.setItems(listModel.programList);
        programColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getProgramName()));

    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                break;
            case "coach":
                break;
            default:
                programBtnBar.setVisible(false);
                break;
        }
    }


}
