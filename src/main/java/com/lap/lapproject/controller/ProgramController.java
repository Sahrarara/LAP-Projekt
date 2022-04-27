package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.database.DatabaseUtility;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.model.UserData;
import javafx.beans.property.SimpleObjectProperty;
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

public class ProgramController {
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
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_PROGRAM));
        Scene scene = null;

        try {
            scene= new Scene(fxmlLoader.load());
        } catch (IOException e){
            e.printStackTrace();
        }

        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onDeleteBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {
    }


    @FXML
    private void initialize(){
        authorityVisibility();


        DatabaseUtility.getProgram();
        initTableProgram();
    }

    private void initTableProgram() {
        tableViewProgram.setItems(ListModel.programList);
        programColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getProgram()));
    }

    private void authorityVisibility(){
        String authority = UserData.authority;
        switch (authority){
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
