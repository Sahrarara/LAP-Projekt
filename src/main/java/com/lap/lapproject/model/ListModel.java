package com.lap.lapproject.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListModel {

    public static ObservableList<Location> locationList = FXCollections.observableArrayList();

    public static  ObservableList<Program> programList = FXCollections.observableArrayList();

    public static ObservableList<Room> roomList = FXCollections.observableArrayList();

    public static ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();

    public static ObservableList<Trainer> trainerList = FXCollections.observableArrayList();
    public static ObservableList<Course> courseList = FXCollections.observableArrayList();
    public static ObservableList<Booking> bookingList = FXCollections.observableArrayList();

}
