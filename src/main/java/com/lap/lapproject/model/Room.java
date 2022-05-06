package com.lap.lapproject.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Room {

    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private long id;
    private String roomNumber;
    private SimpleIntegerProperty size;
    private Location location;
    private byte[] photo;
    private Equipment equipment;


    public Room(long id, String roomNumber, int size, Location location) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
    }

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    //TODO: photo, equipment
    public Room(String roomNumber, int size, Location location, byte[] photo, Equipment equipment) {
        this.roomNumber = roomNumber;
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
        this.photo = photo;
        this.equipment = equipment;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getSize() {
        return size.get();
    }

    public SimpleIntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

}
