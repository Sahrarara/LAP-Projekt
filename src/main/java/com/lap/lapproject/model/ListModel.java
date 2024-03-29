package com.lap.lapproject.model;

import com.lap.lapproject.repos.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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


    public FilteredList<Booking> filteredListForComboBox = new FilteredList<>(bookingList);

    public FilteredList<Course> filteredCourseList = new FilteredList<Course>(courseList);
    public FilteredList<Program> filteredProgramList = new FilteredList<Program>(programList);
    public FilteredList<Equipment> filteredEquipmentList = new FilteredList<Equipment>(equipmentList);
    public FilteredList<Location> filteredLocationList = new FilteredList<Location>(locationList);
    public FilteredList<Booking> filteredBookingList = new FilteredList<Booking>(bookingList);
    public FilteredList<Booking> filteredBookingListForTrainer = new FilteredList<>(bookingList);
    public FilteredList<Trainer> filteredTrainerList = new FilteredList<Trainer>(trainerList);
    public FilteredList<Room> filteredRoomList = new FilteredList<Room>(roomList);
    public FilteredList<Trainer> filteredAuthorizationList = new FilteredList<Trainer>(authorizationList);

    public SortedList<Course> sortedCourseList = new SortedList<Course>(filteredCourseList);
    public SortedList<Program> sortedProgramList = new SortedList<Program>(filteredProgramList);
    public SortedList<Equipment> sortedEquipmentList = new SortedList<Equipment>(filteredEquipmentList);
    public SortedList<Location> sortedLocationList = new SortedList<Location>(filteredLocationList);
    public SortedList<Booking> sortedBookingList = new SortedList<Booking>(filteredBookingList);
    public SortedList<Trainer> sortedTrainerList = new SortedList<Trainer>(filteredTrainerList);
    public SortedList<Room> sortedRoomList = new SortedList<Room>(filteredRoomList);
    public SortedList<Trainer> sortedAuthorizationList = new SortedList<Trainer>(filteredAuthorizationList);

    CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
    ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
    EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
    BookingRepositoryJDBC bookingRepositoryJDBC = new BookingRepositoryJDBC();
    UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
    RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();

    // automatically update photo in sidebar
    private ObjectProperty<Image> userImgProperty = new SimpleObjectProperty<>();


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
            courseList.setAll(courseRepositoryJDBC.readAll());
            programList.setAll(programRepositoryJDBC.readProgram());
            equipmentList.setAll(equipmentRepositoryJDBC.readAll());
            locationList.setAll(locationRepositoryJDBC.readAll());
            bookingList.setAll(bookingRepositoryJDBC.readAll());
            trainerList.setAll(userRepositoryJDBC.readAllTrainer());
            roomList.setAll(roomRepositoryJDBC.readAll());
            authorizationList.setAll(userRepositoryJDBC.readAuthorization());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addListenerForBooking();
        addListenerForUser();
        addListenerForProgram();
        addListenerForEquipment();
        addListenerForLocation();


    }

    public void addListenerForUserImage(Circle bannerImg) {
        userImgProperty.addListener(new ChangeListener<Image>() {
            @Override
            public void changed(ObservableValue<? extends Image> observableValue, Image image, Image t1) {
                bannerImg.setFill(new ImagePattern(userImgProperty.get()));
            }
        });
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

    public void addListenerForProgram() {
        programList.addListener(new ListChangeListener<Program>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Program> change) {
                while (change.next()) {

                    if (change.wasReplaced()) {
                        for (Program program : change.getAddedSubList()) {
                            try {
                                programRepositoryJDBC.updateProgram(program);
                                log.info("from addListenerForProgram: {}", "program updated");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (change.wasAdded()) {
                        for (Program program : change.getAddedSubList()) {
                            try {
                                programRepositoryJDBC.addProgram(program);  //Program is added to a list. programview list
                                log.info("from addListenerForProgram: {}", "program added");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (Program program : change.getRemoved()) {
                            try {
                                programRepositoryJDBC.deleteProgram(program);
                                log.info("from addListenerForProgram: {}", "program deleted");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    public void addListenerForEquipment() {
        equipmentList.addListener(new ListChangeListener<Equipment>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Equipment> change) {
                while (change.next()) {

                    if (change.wasReplaced()) {
                        for (Equipment equipment : change.getAddedSubList()) {
                            try {
                                equipmentRepositoryJDBC.updateEquipment(equipment);
                                log.info("from addListenerForEquipment: {}", "equipment updated");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (change.wasAdded()) {
                        for (Equipment equipment : change.getAddedSubList()) {
                            try {
                                equipmentRepositoryJDBC.addEquipment(equipment);  //Equipment is added to a list. equipmenzview list
                                log.info("from addListenerForEquipment: {}", "equipment added");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (Equipment equipment : change.getRemoved()) {
                            try {
                                equipmentRepositoryJDBC.deleteEquipment(equipment);
                                log.info("from addListenerForEquipment: {}", "equipment deleted");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    public void addListenerForLocation() {
        locationList.addListener(new ListChangeListener<Location>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Location> change) {
                while (change.next()) {

                    if (change.wasReplaced()) {
                        for (Location location : change.getAddedSubList()) {
                            try {
                                locationRepositoryJDBC.updateLocation(location);
                                log.info("from addListenerForLocation: {}", "location updated");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                    } else if (change.wasAdded()) {
                        for (Location location : change.getAddedSubList()) {
                            try {
                                locationRepositoryJDBC.addLocation(location);  //Equipment is added to a list. equipmenzview list
                                log.info("from addListenerForLocation: {}", "location added");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (change.wasRemoved()) {
                        for (Location location : change.getRemoved()) {
                            try {
                                locationRepositoryJDBC.deleteLocation(location);
                                log.info("from addListenerForLocation: {}", "location deleted");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

   /*public void addListenerForProgram() {
        programList.addListener(new ListChangeListener<Program>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Program> change) {
                while (change.next()) {

                    if (change.wasReplaced()) {//gepr ob was updated
                        for (Program program : change.getAddedSubList()) { //liste für buch, ob was updated ist
                            try {
                                programRepositoryJDBC.updateProgram(program);
                                courseRepositoryJDBC.readAll();
                                courseList.setAll(courseRepositoryJDBC.readAll());
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
                                bookingList.setAll(bookingRepositoryJDBC.readAll());
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

    public Image getCurrentUserImage() {
        return userImgProperty.get();
    }

    public void setUserImgProperty(Image userImage) {
        this.userImgProperty.set(userImage);
    }
}
