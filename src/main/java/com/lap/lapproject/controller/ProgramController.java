package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.model.UserData;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

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
    private TableColumn<Program, Boolean> checkBoxColumn;
    @FXML
    private TableColumn<Program, String> programColumn;

    @FXML
    private void onAddProgramBtnClick(ActionEvent actionEvent) {
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
    }

    @FXML
    private void onSettingsBtnClick(ActionEvent actionEvent) {
    }

    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {
    }


    @FXML
    private void initialize() throws SQLException {
        assert tableViewProgram != null : "fx:id=\"tableViewProgram\" was not injected: check your FXML file 'program-view.fxml'.";
        assert checkBoxColumn != null : "fx:id=\"checkBoxColumn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert programColumn != null : "fx:id=\"programColumn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert addProgramBtn != null : "fx:id=\"addProgramBtn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'program-view.fxml'.";
        assert programBtnBar != null : "fx:id=\"programBtnBar\" was not injected: check your FXML file 'program-view.fxml'.";
        assert settingsBtn != null : "fx:id=\"settingsBtn\" was not injected: check your FXML file 'program-view.fxml'.";


        authorityVisibility();
        ProgramRepositoryJDBC programRepo = new ProgramRepositoryJDBC();
        programRepo.getProgram();
        initTableProgram();
    }

    private void initTableProgram() {
        tableViewProgram.setItems(ListModel.programList);
        programColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getProgramName()));
        checkBoxColumn.setCellValueFactory((dataFeatures) -> dataFeatures.getValue().checkedProperty());
        checkBoxColumn.setCellFactory(new Callback<TableColumn<Program, Boolean>, TableCell<Program, Boolean>>() {
            @Override
            public TableCell<Program, Boolean> call(TableColumn<Program, Boolean> userBooleanTableColumn) {
                TableCell<Program, Boolean> cell = new TableCell<>() {
                    CheckBox checkBox = new CheckBox();

                    @Override
                    protected void updateItem(Boolean value, boolean empty) {
                        super.updateItem(value, empty);
                        if (empty) { //wenn kein inhalt
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(null);
                            setGraphic(checkBox);
                            checkBox.setSelected(value);
                            // System.out.println(checkBox.isSelected());
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    private void authorityVisibility() {
        String authority = UserData.authority;
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
