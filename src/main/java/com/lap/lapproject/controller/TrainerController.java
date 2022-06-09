package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Room;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;


public class TrainerController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    @FXML
    private ButtonBar trainerBtnBar;


    @FXML
    private TableView<Trainer> tableViewTrainer;
    @FXML
    private TableColumn<Trainer, String> firstNameColumn;
    @FXML
    private TableColumn<Trainer, String> lastNameColumn;
    @FXML
    private TableColumn<Trainer, String> emailColumn;
    @FXML
    private TableColumn<Trainer, String> phoneColumn;
    @FXML
    private TableColumn<Trainer, Boolean> activeStatusColumn;
    @FXML
    private ChoiceBox filterChoiceBox;
    @FXML
    private TextField searchBar;
    @FXML
    private TableColumn<Trainer, ImageView> trainerImg;
    @FXML
    private TableColumn<Trainer, String> descriptionColumn;



    @FXML
    private void onAddTrainerBtnClick(ActionEvent actionEvent) {
        tableViewTrainer.getSelectionModel().select(null);
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_TRAINER));
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
    private void onDeleteTrainerBtnClick(ActionEvent actionEvent) {
        BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
        Trainer trainer = tableViewTrainer.getSelectionModel().getSelectedItem();

        //Alert CONFIRMATION TODO: wenn es möglich nur einen CONFIRMATION Alert für Alle DELETE
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
        Optional<ButtonType> action = alert.showAndWait();
        if(action.get() == ButtonType.OK) {
            // check in DB how many bookings use the particular location
            int bookingCountByTrainer = bookingRepositoryJDBC.getBookingCountByTrainerId(trainer.getId());

            if (bookingCountByTrainer == 0) {

                listModel.trainerList.remove(trainer);
            } else {
                QuickAlert.showError("Diese : er Trainer  wird für eine Buchung benötigt, Sie können sie:ihn nicht löschen! Bearbeiten Sie zuerst Ihre Buchungen!");
            }

        }
    }

    @FXML
    private void onEditBtnClick(ActionEvent actionEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_TRAINER));
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
    private void initialize() throws IOException {
        assert trainerImg != null : "fx:id=\"bannerImg\" was not injected: check your FXML file 'trainer.fxml'.";
        assert tableViewTrainer != null : "fx:id=\"tableViewTrainer\" was not injected: check your FXML file 'trainer.fxml'.";
        assert emailColumn != null : "fx:id=\"emailColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert firstNameColumn != null : "fx:id=\"firstNameColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert lastNameColumn != null : "fx:id=\"lastNameColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert phoneColumn != null : "fx:id=\"phoneColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert activeStatusColumn != null : "fx:id=\"activeStatusColumn\" was not injected: check your FXML file 'trainer.fxml'.";
        assert trainerBtnBar != null : "fx:id=\"trainerBtnBar\" was not injected: check your FXML file 'trainer.fxml'.";

        authorityVisibility();
        initTrainerTable();
        //nimmt daten von tabele und befüllt das Formular
         listModel.selectedUserProperty().bind(tableViewTrainer.getSelectionModel().selectedItemProperty());
    }

    private void initTrainerTable() throws IOException {
        BufferedImage bImage = ImageIO.read(new FileInputStream("src/main/resources/com/lap/lapproject/images/lapproject/images/user.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] imageNoVisible = bos.toByteArray();
        String notVisibleText = " ";

        tableViewTrainer.setItems(listModel.trainerList);
        //trainerImg.setPrefWidth(200);

        trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream((DataFeatures.getValue().getPhotoVisibility() || (model.getAuthority().equals("admin") && DataFeatures.getValue().getPhoto() != null)) ? DataFeatures.getValue().getPhoto() : imageNoVisible), 140, 200, false, false)))));


        firstNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getfName() + " " + dataFeatures.getValue().getlName()));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>((dataFeatures.getValue().getDescriptionVisibility() || model.getAuthority().equals("admin")) ? dataFeatures.getValue().getDescription() : notVisibleText));
        emailColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>((dataFeatures.getValue().getEmailVisibility() || model.getAuthority().equals("admin")) ? dataFeatures.getValue().getEmail() : notVisibleText));


        phoneColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>((dataFeatures.getValue().getPhoneNmbrVisibility() || model.getAuthority().equals("admin"))  ? dataFeatures.getValue().getPhoneNmbr() : notVisibleText ));

        activeStatusColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getActiveStatus()));


      /*  if(model.getLoggedInUser().getPhoto() != null) {
            trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream(DataFeatures.getValue().getPhotoVisibility() ? DataFeatures.getValue().getPhoto() : imageNoVisible), 140, 200, false, false)))));
            listModel.ListModel();
        } else {
        trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream(DataFeatures.getValue().getPhoto() : imageNoVisible), 140, 200, false, false)))));
        }*/


        //wechsselt boolean Value auf Text ja oder nein
        activeStatusColumn.setCellFactory(col -> new TableCell<Trainer, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "Ja" : "Nein");
            }
        });
    }

    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                break;
            case "coach":
                trainerBtnBar.setVisible(false);
                break;
            default:
                trainerBtnBar.setVisible(false);
                break;
        }
    }

    @FXML
    private void onSearchBarClick(ActionEvent actionEvent) {

        String searchTerm = searchBar.getText();
        ObservableList<Trainer> filteredList = FXCollections.observableArrayList();

        if (searchTerm.equals("")) {
            filteredList = listModel.trainerList;
        } else {
            for (Trainer elem : listModel.trainerList) {

                if (elem.getfName().toUpperCase().contains(searchTerm.toUpperCase())
                        || elem.getlName().toUpperCase().contains(searchTerm.toUpperCase())
                        || elem.getEmail().toUpperCase().contains(searchTerm.toUpperCase())
                        || elem.getPhoneNmbr().contains(searchTerm)
//                        || (elem.getActiveStatus() && searchTerm.equalsIgnoreCase("ja"))
//                        || (!elem.getActiveStatus() && searchTerm.equalsIgnoreCase("nein"))

                ) {
                    filteredList.add(elem);
                }
            }
        }
        tableViewTrainer.setItems(filteredList);
    }

    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }
}
