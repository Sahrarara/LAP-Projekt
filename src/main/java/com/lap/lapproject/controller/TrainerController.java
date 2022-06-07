package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.Trainer;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import com.lap.lapproject.repos.UserRepositoryJDBC;
import com.lap.lapproject.utility.QuickAlert;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;


public class TrainerController extends BaseController{
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
        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
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

                userRepositoryJDBC.deleteUser(trainer);
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
        trainerImg.setPrefWidth(200);

         if(model.getLoggedInUser().getPhoto() != null) {
        trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream(DataFeatures.getValue().getPhotoVisibility() ? DataFeatures.getValue().getPhoto() : imageNoVisible), 140, 200, false, false)))));
        //trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream(DataFeatures.getValue().getPhotoVisibility() ? DataFeatures.getValue().getPhoto() : imageNoVisible), 140, 200, false, false)))));
        //trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream(DataFeatures.getValue().getPhoto()), 140, 200, false, false)))));
       }else {
            trainerImg.setCellValueFactory((DataFeatures -> new SimpleObjectProperty<ImageView>(new ImageView(new Image(new ByteArrayInputStream(imageNoVisible), 140, 200, false, false)))));
        }

        //trainerImg.setCellValueFactory((DataFeatures -> (ObservableValue<Byte>) new ImageView(String.valueOf(DataFeatures.getValue().getPhoto()))));
        firstNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getfName() + " " + dataFeatures.getValue().getlName()));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getDescriptionVisibility() ? dataFeatures.getValue().getDescription() : notVisibleText));
        emailColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getEmailVisibility() ? dataFeatures.getValue().getEmail() : notVisibleText));


        phoneColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getPhoneNmbrVisibility() ? dataFeatures.getValue().getPhoneNmbr() : notVisibleText ));

        activeStatusColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getActiveStatus()));


        /*if(model.getLoggedInUser().getPhoto() != null) {
            trainerImg.setFill(new ImagePattern(imageFromBytes(model.getLoggedInUser().getPhoto())));
            listModel.ListModel();
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
        listModel.filteredTrainerList.setPredicate(trainer -> trainer.getfName().toLowerCase(Locale.ROOT).contains(searchBar.getText().toLowerCase(Locale.ROOT)));
    }
}
