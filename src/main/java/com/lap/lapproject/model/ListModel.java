package com.lap.lapproject.model;

import com.lap.lapproject.repos.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ListModel {

    private static final Logger log = LoggerFactory.getLogger(ListModel.class);

    public ObservableList<Course> courseList = FXCollections.observableArrayList();
    public ObservableList<Program> programList = FXCollections.observableArrayList();
    public ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();
    public ObservableList<Location> locationList = FXCollections.observableArrayList();
    public ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    public ObservableList<Trainer> trainerList = FXCollections.observableArrayList();
    public ObservableList<Trainer> activeTrainerList = FXCollections.observableArrayList();
    public ObservableList<Room> roomList = FXCollections.observableArrayList();
    public ObservableList<Trainer> authorizationList = FXCollections.observableArrayList();

    public FilteredList<Course> filteredCourseList = new FilteredList<Course>(courseList);
    public FilteredList<Program> filteredProgramList = new FilteredList<Program>(programList);
    public FilteredList<Equipment> filteredEquipmentList = new FilteredList<Equipment>(equipmentList);
    public FilteredList<Location> filteredLocationList = new FilteredList<Location>(locationList);
    public FilteredList<Booking> filteredBookingList = new FilteredList<Booking>(bookingList);
    public FilteredList<Trainer> filteredTrainerList = new FilteredList<Trainer>(trainerList);
    public FilteredList<Room> filteredRoomList = new FilteredList<Room>(roomList);
    public FilteredList<Trainer> filteredAuthorizationList = new FilteredList<Trainer>(authorizationList);

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
    private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<>();
    private ObjectProperty<Trainer> selectedUser = new SimpleObjectProperty<>();
    private ObjectProperty<Room> selectedRoom = new SimpleObjectProperty<>();


    public ListModel() {

        try {
            /*courseList.addAll(courseRepositoryJDBC.readAll());
            programList.addAll(programRepositoryJDBC.readProgram());
            equipmentList.addAll(equipmentRepositoryJDBC.readAll());
            locationList.addAll(locationRepositoryJDBC.readAll());
            bookingList.addAll(bookingRepositoryJDBC.readAll());
            trainerList.addAll(userRepositoryJDBC.readAllTrainer());
            roomList.addAll(roomRepositoryJDBC.readAll());
            authorizationList.addAll(userRepositoryJDBC.readAuthorization());*/

            courseList.setAll(courseRepositoryJDBC.readAll());
            programList.setAll(programRepositoryJDBC.readProgram());
            equipmentList.setAll(equipmentRepositoryJDBC.readAll());
            locationList.setAll(locationRepositoryJDBC.readAll());
            bookingList.setAll(bookingRepositoryJDBC.readAll());
            trainerList.setAll(userRepositoryJDBC.readAllTrainer());
            roomList.setAll(roomRepositoryJDBC.readAll());
            authorizationList.setAll(userRepositoryJDBC.readAuthorization());
            //System.out.println("+++++ Fill All MODEL lists!!!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //addAllListeners();
        addListenerForBooking();
        addListenerForUser();


    }


    public void addListenerForBooking() {
        bookingList.addListener(new ListChangeListener<Booking>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Booking> change) {
                while (change.next()) {

                    if (change.wasReplaced()) {
                        for (Booking booking : change.getAddedSubList()) {
                            try {
                                bookingRepositoryJDBC.updateBooking(booking);
                                log.info("from addListenerForBooking: {}", "booking updated");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (change.wasAdded()) {
                        for (Booking booking : change.getAddedSubList()) {
                            try {
                                bookingRepositoryJDBC.addBooking(booking);
                                log.info("from addListenerForBooking: {}", "booking added");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (Booking booking : change.getRemoved()) {
                            try {
                                bookingRepositoryJDBC.deleteBooking(booking);
                                log.info("from addListenerForBooking: {}", "booking deleted");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

   /* public void addListenerForProgram() {
        programList.addListener(new ListChangeListener<Program>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Program> change) {
                while (change.next()) {

                    if (change.wasReplaced()) {//gepr ob was updated
                        for (Program program : change.getAddedSubList()) { //liste für buch, ob was updated ist
                            try {
                                programRepositoryJDBC.updateProgram(program);
                                courseRepositoryJDBC.readAll();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (change.wasAdded()) {//gepr ob was geadd
                        for (Program program : change.getAddedSubList()) { //liste für buch, ob was geadd ist
                            try {
                                programRepositoryJDBC.addProgram(program); // add book zu repo
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasRemoved()) {//gepr ob was deleted
                        for (Program program : change.getRemoved()) { //liste für buch, ob was gelöscht ist
                            try {
                                programRepositoryJDBC.deleteProgram(program); // delete from repo
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }*/




    public void addListenerForUser() {
        trainerList.addListener(new ListChangeListener<Trainer>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Trainer> change) {
                while (change.next()) {
                    if (change.wasReplaced()) {
                        for (Trainer trainer : change.getAddedSubList()) {
                            try {
                                userRepositoryJDBC.updateUser(trainer);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasAdded()) {
                        for (Trainer trainer : change.getAddedSubList()) {
                            try {
                                userRepositoryJDBC.add(trainer);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (Trainer trainer : change.getRemoved()) {
                            try {
                                userRepositoryJDBC.deleteUser(trainer);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
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


    public Location getSelectedLocation() {
        return selectedLocation.get();
    }
    public ObjectProperty<Location> selectedLocationProperty() {
        return selectedLocation;
    }
    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation.set(selectedLocation);
    }


    public Course getSelectedCourse() {
        return selectedCourse.get();
    }
    public ObjectProperty<Course> selectedCourseProperty() {
        return selectedCourse;
    }
    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse.set(selectedCourse);
    }


    public Trainer getSelectedUser() {
        return selectedUser.get();
    }
    public ObjectProperty<Trainer> selectedUserProperty() {
        return selectedUser;
    }
    public void setSelectedUser(Trainer selectedUser) {
        this.selectedUser.set(selectedUser);
    }


    public Room getSelectedRoom() {
        return selectedRoom.get();
    }
    public ObjectProperty<Room> selectedRoomProperty() {
        return selectedRoom;
    }
    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom.set(selectedRoom);
    }


    public ObservableList<Program> getProgramList() {
        return programList;
    }
    public void setProgramList(ObservableList<Program> programList) {
        this.programList = programList;
    }
}
