package com.lap.lapproject.model;

import com.lap.lapproject.repos.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ListModel {

    private static final Logger log = LoggerFactory.getLogger(ListModel.class);

    public ObservableList<Course> courseList = FXCollections.observableArrayList();
    public  ObservableList<Program> programList = FXCollections.observableArrayList();
    public ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();
    public  ObservableList<Location> locationList = FXCollections.observableArrayList();
    public  ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    public ObservableList<Trainer> trainerList = FXCollections.observableArrayList();
    public ObservableList<Room> roomList = FXCollections.observableArrayList();



    CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
    ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
    EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
    BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
    UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
    RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();


//UPDATE Variable enthält den Wert des ausgewählten Elements
    private ObjectProperty<Program> selectedProgram = new SimpleObjectProperty<>();
    private ObjectProperty<Equipment> selectedEquipment = new SimpleObjectProperty<>();
    private ObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();
    private ObjectProperty<Location> selectedLocation = new SimpleObjectProperty<>();



    public ListModel() {

        try {
            courseList.addAll(courseRepositoryJDBC.readAll());
            programList.addAll(programRepositoryJDBC.readProgram());
            equipmentList.addAll(equipmentRepositoryJDBC.readAll());
            locationList.addAll(locationRepositoryJDBC.readAll());
            bookingList.addAll(bookingRepositoryJDBC.readAll());
            trainerList.addAll(userRepositoryJDBC.readAllTrainer());
            roomList.addAll(roomRepositoryJDBC.readAll());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Program getSelectedProgram() {
        return selectedProgram.get();
    }

    public ObjectProperty<Program> selectedProgramProperty() {
        return selectedProgram;
    }

    public void setSelectedProgram(Program selectedProgram) {
        this.selectedProgram.set(selectedProgram);
    }



    public Equipment getSelectedEquipment() {
        return selectedEquipment.get();
    }

    public ObjectProperty<Equipment> selectedEquipmentProperty() {
        return selectedEquipment;
    }

    public void setSelectedEquipment(Equipment selectedEquipment) {
        this.selectedEquipment.set(selectedEquipment);
    }



    public Booking getSelectedBooking() {
        return selectedBooking.get();
    }
    public ObjectProperty<Booking> selectedBookingProperty() {
        return selectedBooking;
    }
    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking.set(selectedBooking);
    }


    public Location getSelectedLocation() {return selectedLocation.get();}

    public ObjectProperty<Location> selectedLocationProperty() {
        return selectedLocation;
    }

    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation.set(selectedLocation);
    }

}
