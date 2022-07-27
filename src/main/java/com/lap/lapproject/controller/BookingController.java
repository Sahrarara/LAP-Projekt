package com.lap.lapproject.controller;

import com.lap.lapproject.LoginApplication;
import com.lap.lapproject.application.Constants;
import com.lap.lapproject.model.*;
import com.lap.lapproject.utility.QuickAlert;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.print.Book;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static com.lap.lapproject.controller.BaseController.listModel;
import static com.lap.lapproject.controller.BaseController.model;

public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @FXML
    private TableView<Booking> tableViewBooking;
    @FXML
    private TableColumn<Booking, String> locationColumn;
    @FXML
    private TableColumn<Booking, Integer> roomColumn;
    @FXML
    private TableColumn<Booking, String> trainerColumn;
    @FXML
    private TableColumn<Booking, String> dateFromColumn;
    @FXML
    private TableColumn<Booking, String> dateToColumn;
    @FXML
    private TableColumn<Booking, String> dateTimeColumn;
    @FXML
    private TableColumn<Booking, String> recurrenceRuleColumn;
    @FXML
    private TableColumn<Booking, String> courseNameColumn;
    @FXML
    private TextField searchBar;
    @FXML
    private Button closeIconButton;
    @FXML
    private HBox bookingBtnBarForCoach;
    @FXML
    private ButtonBar bookingBtnBarForAdmin;
    @FXML
    private ComboBox<Course> courseNameComboBox;
    @FXML
    private ComboBox<Location> locationComboBox;
    @FXML
    private ComboBox<Room> roomComboBox;
    @FXML
    private ComboBox<Trainer> trainerComboBox;
    @FXML
    private HBox comboBoxHBox;
    @FXML
    private ButtonBar bookingBtnBarForCoachList;
    @FXML
    private GridPane gridWithButtonsForCoachList;
    @FXML
    private Button clearFilterButtonForCoach;
    @FXML
    private Button filterButtonForCoach;
    @FXML
    private GridPane gridWithButtonsForCoach;
    @FXML
    private Button clearFilterButtonForAdmin;
    @FXML
    private Button filterButtonForAdmin;
    @FXML
    private Button editButtonForAdmin;
    @FXML
    private Button deleteButtonForAdmin;
    @FXML
    private GridPane gridWithButtonsForAdmin;


    @FXML
    private void onAddBookingBtnClick(ActionEvent actionEvent) {
        tableViewBooking.getSelectionModel().select(null);
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_BOOKING));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Raum Management");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void onDeleteBookingBtnClick(ActionEvent actionEvent) {
        if (listModel.getSelectedBooking() != null) {
            Booking booking = tableViewBooking.getSelectionModel().getSelectedItem();
            //  BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
            //bookingRepositoryJDBC.deleteBooking(booking);

            //Alert CONFIRMATION
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Sind Sie sicher, dass Sie es löschen wollen?");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                listModel.bookingList.remove(booking);
            }
        } else {
            QuickAlert.showInfo("Bitte gewünschte Zeile markieren");
        }
    }

    @FXML
    private void onEditBtnClick(ActionEvent actionEvent) {
        if (listModel.getSelectedBooking() != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource(Constants.PATH_TO_FXML_CREATE_NEW_BOOKING));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Raum Management");
            stage.setScene(scene);
            stage.show();
        } else {
            QuickAlert.showInfo("Bitte gewünschte Zeile markieren");
        }

    }

    @FXML
    void initialize() {
        assert bookingBtnBarForAdmin != null : "fx:id=\"bookingBtnBarForAdmin\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert bookingBtnBarForCoach != null : "fx:id=\"bookingBtnBarForCoach\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert bookingBtnBarForCoachList != null : "fx:id=\"bookingBtnBarForCoachList\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert clearFilterButtonForAdmin != null : "fx:id=\"clearFilterButtonForAdmin\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert clearFilterButtonForCoach != null : "fx:id=\"clearFilterButtonForCoach\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert closeIconButton != null : "fx:id=\"closeIconButton\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert comboBoxHBox != null : "fx:id=\"comboBoxHBox\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert courseNameColumn != null : "fx:id=\"courseNameColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert courseNameComboBox != null : "fx:id=\"courseNameComboBox\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateFromColumn != null : "fx:id=\"dateFromColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateTimeColumn != null : "fx:id=\"dateTimeColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert dateToColumn != null : "fx:id=\"dateToColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert deleteButtonForAdmin != null : "fx:id=\"deleteButtonForAdmin\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert editButtonForAdmin != null : "fx:id=\"editButtonForAdmin\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert filterButtonForAdmin != null : "fx:id=\"filterButtonForAdmin\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert filterButtonForCoach != null : "fx:id=\"filterButtonForCoach\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert gridWithButtonsForAdmin != null : "fx:id=\"gridWithButtonsForAdmin\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert gridWithButtonsForCoach != null : "fx:id=\"gridWithButtonsForCoach\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert gridWithButtonsForCoachList != null : "fx:id=\"gridWithButtonsForCoachList\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert recurrenceRuleColumn != null : "fx:id=\"recurrenceRuleColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert roomColumn != null : "fx:id=\"roomColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert roomComboBox != null : "fx:id=\"roomComboBox\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert searchBar != null : "fx:id=\"searchBar\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert tableViewBooking != null : "fx:id=\"tableViewBooking\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert trainerColumn != null : "fx:id=\"trainerColumn\" was not injected: check your FXML file 'booking-view.fxml'.";
        assert trainerComboBox != null : "fx:id=\"trainerComboBox\" was not injected: check your FXML file 'booking-view.fxml'.";

        closeIconButton.setVisible(false);
        UsabilityMethods.changeListener(searchBar, closeIconButton);

        authorityVisibility();
        initBookingTable();

        // Damit werden alle Choice-Boxen mit Daten aus der selektierte Tabellenzeile befüllt:
        listModel.selectedBookingProperty().bind(tableViewBooking.getSelectionModel().selectedItemProperty());

        //combobox
        listModel.selectedCourseProperty().bind(courseNameComboBox.valueProperty());
        listModel.selectedLocationProperty().bind(locationComboBox.valueProperty());
        listModel.selectedRoomProperty().bind(roomComboBox.valueProperty());
        listModel.selectedUserProperty().bind(trainerComboBox.valueProperty());
//        comboBoxHBox.setVisible(false);
        changeListenerCourseName();
        changeListenerLocation();
        changeListenerRoomNr();
        changeListenerTrainer();

        clearFilterButtonForAdmin.setVisible(false);
        clearFilterButtonForCoach.setVisible(false);

    }


    public void initBookingTable() {
        tableViewBooking.setItems(listModel.filteredListForComboBox);

        courseNameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getCourse().getCourseName()));
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getLocation().getStreet()));
        roomColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRoom().getRoomNumber()));

        trainerColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getTrainer().getfName() + " " +
                dataFeatures.getValue().getTrainer().getlName()));

        recurrenceRuleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<>(dataFeatures.getValue().getRecurrenceRule()));

        dateFromColumn.setCellValueFactory((dataFeatures) ->
                new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toLocalDate().
                        format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replaceAll("-", ".")));

        dateToColumn.setCellValueFactory((dataFeatures) ->
                new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeEnd().toLocalDate().
                        format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).replaceAll("-", ".")));

        dateTimeColumn.setCellValueFactory((dataFeatures) ->
                new SimpleObjectProperty<>(dataFeatures.getValue().getDateTimeStart().toLocalTime().toString().substring(0, 5)
                        + " - " + dataFeatures.getValue().getDateTimeEnd().toLocalTime().toString().substring(0, 5)));
    }


    private void authorityVisibility() {
        String authority = model.getAuthority();
        switch (authority) {
            case "admin":
                gridWithButtonsForCoach.setVisible(false);
                gridWithButtonsForCoachList.setVisible(false);
                break;
            case "coach":
                gridWithButtonsForAdmin.setVisible(false);
                gridWithButtonsForCoachList.setVisible(false);
                break;
            default:
                gridWithButtonsForAdmin.setVisible(false);
                gridWithButtonsForCoach.setVisible(false);
                gridWithButtonsForCoachList.setVisible(false);
        }
    }


    @FXML
    private void onToMyListButtonClick(ActionEvent actionEvent) {
        logger.info("Eingeloggte User: {}", model.getLoggedInUser().getId() + " " + model.getLoggedInUser().getUsername());

        listModel.filteredBookingListForTrainer.setPredicate(booking -> booking.getTrainer().getId() == model.getLoggedInUser().getId());
        tableViewBooking.setItems(listModel.filteredBookingListForTrainer);
        gridWithButtonsForCoach.setVisible(false);
        gridWithButtonsForCoachList.setVisible(true);
    }


    @FXML
    private void onBackToListBtnClick(ActionEvent actionEvent) {
        tableViewBooking.setItems(listModel.bookingList);
        gridWithButtonsForCoach.setVisible(true);
        gridWithButtonsForCoachList.setVisible(false);
    }



    // combobox
    @FXML
    private void onFilterButtonClick(ActionEvent actionEvent) {

        comboBoxHBox.setVisible(true);

//        tableViewBooking.setItems(listModel.filteredListForComboBox);

        courseNameComboBox.setItems(listModel.courseList);
        locationComboBox.setItems(listModel.locationList);
        roomComboBox.setItems(listModel.roomList);
        trainerComboBox.setItems(listModel.trainerList);

        if (model.getAuthority().equals("admin")) {
            clearFilterButtonForAdmin.setVisible(true);
        } else if (model.getAuthority().equals("coach")) {
            clearFilterButtonForCoach.setVisible(true);
        }

    }


    @FXML
    private void onClearFilterButtonClick(ActionEvent actionEvent) {
        comboBoxHBox.setVisible(false);

        if (model.getAuthority().equals("admin")) {
            clearFilterButtonForAdmin.setVisible(false);
        } else if (model.getAuthority().equals("coach")) {
            clearFilterButtonForCoach.setVisible(false);
        }

        predicateMap.clear();
        Predicate<Booking> combinedPredicate = predicateMap.values().stream().reduce(x -> true, Predicate::and);
        listModel.filteredListForComboBox.setPredicate(combinedPredicate);

        clearComboBoxSelection();
    }



    public void clearComboBoxSelection() {
        courseNameComboBox.getSelectionModel().clearSelection();
        courseNameComboBox.setButtonCell(new ListCell<Course>());

        locationComboBox.getSelectionModel().clearSelection();
        locationComboBox.setButtonCell(new ListCell<Location>());

        roomComboBox.getSelectionModel().clearSelection();
        roomComboBox.setButtonCell(new ListCell<Room>());

        trainerComboBox.getSelectionModel().clearSelection();
        trainerComboBox.setButtonCell(new ListCell<Trainer>());
    }



    Map<Integer, Predicate> predicateMap = new HashMap<>();


    Predicate<Booking> courseNamePredicate = booking -> booking.getCourse().getCourseName().toLowerCase(Locale.ROOT)
            .contains(listModel.getSelectedCourse().getCourseName().toLowerCase(Locale.ROOT));

    Predicate<Booking> locationPredicate = booking -> booking.getRoom().getLocation().getStreet().toLowerCase(Locale.ROOT)
            .contains(listModel.getSelectedLocation().getStreet().toLowerCase(Locale.ROOT));

    Predicate<Booking> roomPredicate = booking -> String.valueOf(booking.getRoom().getRoomNumber())
            .contains(String.valueOf(listModel.getSelectedRoom().getRoomNumber()));

    Predicate<Booking> trainerPredicate = booking -> booking.getTrainer().getlName().toLowerCase(Locale.ROOT)
            .contains(listModel.getSelectedUser().getlName().toLowerCase(Locale.ROOT));


    private void changeListenerCourseName() {
        listModel.selectedCourseProperty().addListener(new ChangeListener<Course>() {
            @Override
            public void changed(ObservableValue<? extends Course> observableValue, Course oldValue, Course newValue) {
                if (listModel.getSelectedCourse() != null) {
                    predicateMap.put(1, courseNamePredicate);

                } else if (listModel.getSelectedCourse() == null) {
                    predicateMap.remove(1);
//                    predicateMap.forEach((k, v) -> System.out.println("key: " + k + ", value: " + v));
                }

                Predicate<Booking> combinedPredicate = predicateMap.values().stream().reduce(x -> true, Predicate::and);
                listModel.filteredListForComboBox.setPredicate(combinedPredicate);
            }
        });
    }


    private void changeListenerLocation() {
        listModel.selectedLocationProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location oldValue, Location newValue) {
                if (listModel.getSelectedLocation() != null) {
                    predicateMap.put(2, locationPredicate);
                } else {
                    predicateMap.remove(2, locationPredicate);
                }

                Predicate<Booking> combinedPredicate = predicateMap.values().stream().reduce(x -> true, Predicate::and);
                listModel.filteredListForComboBox.setPredicate(combinedPredicate);
            }
        });
    }


    private void changeListenerRoomNr() {
        listModel.selectedRoomProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room oldValue, Room newValue) {
                if (listModel.getSelectedRoom() != null) {
                    predicateMap.put(3, roomPredicate);
                } else {
                    predicateMap.remove(3, roomPredicate);
                }

                Predicate<Booking> combinedPredicate = predicateMap.values().stream().reduce(x -> true, Predicate::and);
                listModel.filteredListForComboBox.setPredicate(combinedPredicate);
            }
        });
    }


    private void changeListenerTrainer() {
        listModel.selectedUserProperty().addListener(new ChangeListener<Trainer>() {
            @Override
            public void changed(ObservableValue<? extends Trainer> observableValue, Trainer oldValue, Trainer newValue) {
                if (listModel.getSelectedUser() != null) {
                    predicateMap.put(4, trainerPredicate);
                } else {
                    predicateMap.remove(4, trainerPredicate);
                }

                Predicate<Booking> combinedPredicate = predicateMap.values().stream().reduce(x -> true, Predicate::and);
                listModel.filteredListForComboBox.setPredicate(combinedPredicate);
            }
        });
    }


    /**
     * Es werden bei eingabe der gesuchten Namen die Daten gefiltert
     * @param actionEvent erwartet keinen Parameter
     */
    @FXML
    private void onSearchBarClick(KeyEvent actionEvent) {
        String searchTerm = searchBar.getText();
        ObservableList<Booking> filteredList = FXCollections.observableArrayList();

        if (searchTerm.equals("")) {
            filteredList = listModel.bookingList;

        } else {

            for (Booking elem : listModel.bookingList) {
                if (
                        elem.getCourse().toString().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getTrainer().getfName().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getTrainer().getlName().toUpperCase().contains(searchTerm.toUpperCase())
                                || String.valueOf(elem.getRoom().getRoomNumber()).contains(searchTerm)
                                || elem.getRoom().getLocation().getStreet().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getRecurrenceRule().toUpperCase().contains(searchTerm.toUpperCase())
                                || elem.getDateTimeStart().toLocalDate().toString().contains(searchTerm)
                                || elem.getDateTimeEnd().toLocalDate().toString().contains(searchTerm)
                                || elem.getDateTimeStart().toLocalTime().toString().contains(searchTerm)
                                || elem.getDateTimeEnd().toLocalTime().toString().contains(searchTerm)
                ) {
                    filteredList.add(elem);
                }
            }
        }
        tableViewBooking.setItems(filteredList);
    }


    @FXML
    private void onCloseIconClick(ActionEvent actionEvent) {
        searchBar.setText("");
    }



}
