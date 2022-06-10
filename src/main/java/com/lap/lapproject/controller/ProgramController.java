package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Program;
import com.lap.lapproject.repos.CourseRepositoryJDBC;
import com.lap.lapproject.repos.ProgramRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
//import com.lap.lapproject.utility.UsabilityMethods;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Locale;

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
    private TextField searchBar;
    @FXML
    private Button closeIconButton;


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
        CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();

        Program programToDelete = tableViewProgram.getSelectionModel().getSelectedItem();

        //Alert CONFIRMATION TODO: wenn es möglich nur einen CONFIRMATION Alert für Alle DELETE
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK) {

            try {
                // check in DB how many courses use the particular program
                int coursesCount = courseRepositoryJDBC.getCoursesCountByProgramId(programToDelete.getId());

                if (coursesCount == 0) {
                    programRepositoryJDBC.deleteProgram(programToDelete);
                    listModel.programList.remove(programToDelete);
                } else {
                    QuickAlert.showError("Dieses Program wird von einem Kurse benötigt! Sie können sie nicht löschen!");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

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

        closeIconButton.setVisible(false);
        UsabilityMethods.changeListener(searchBar, closeIconButton);

        authorityVisibility();
        initTableProgram();
        //nimmt daten von tabele und befüllt das Formular
        listModel.selectedProgramProperty().bind(tableViewProgram.getSelectionModel().selectedItemProperty());

    }

    private void initTableProgram() {
        tableViewProgram.setItems(listModel.sortedProgramList);
        listModel.sortedProgramList.comparatorProperty().bind(tableViewProgram.comparatorProperty());
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



    @FXML
    private void onSearchBarClick(KeyEvent actionEvent) {
        listModel.filteredProgramList.setPredicate(program -> program.getProgramName().toLowerCase(Locale.ROOT).contains(searchBar.getText().toLowerCase(Locale.ROOT)));
    }


    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }
}
