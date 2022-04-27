package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room {
    private SimpleStringProperty roomNumber;
    private SimpleIntegerProperty size;
    private Location location;
    private byte[] photo;
    private Equipment equipment;

    public Room(String roomNumber, int size, Location location) {
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
    }

    //TODO: photo, equipment
    public Room(String roomNumber, int size, Location location, byte[] photo, Equipment equipment) {
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
        this.photo = photo;
        this.equipment = equipment;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleStringProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
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
