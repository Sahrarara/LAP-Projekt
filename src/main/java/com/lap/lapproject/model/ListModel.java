package com.lap.lapproject.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListModel {

    private static final Logger log = LoggerFactory.getLogger(ListModel.class);

    public static ObservableList<Location> locationList = FXCollections.observableArrayList();

    public static  ObservableList<Program> programList = FXCollections.observableArrayList();

    public static ObservableList<Room> roomList = FXCollections.observableArrayList();

    public static ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();

    public static ObservableList<Trainer> trainerList = FXCollections.observableArrayList();
    public static ObservableList<Course> courseList = FXCollections.observableArrayList();
    public static ObservableList<Booking> bookingList = FXCollections.observableArrayList();

}
