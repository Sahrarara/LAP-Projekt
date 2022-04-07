package com.lap.lapproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Room {
    private SimpleStringProperty roomName;
    private int size;
    private Location location;
    private byte[] photo;
    private Equipment equipment;


    public Room(String roomName, int size, Location location, byte[] photo, Equipment equipment) {
        this.roomName = new SimpleStringProperty(roomName);
        this.size = size;
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

    public String getRoomName() {
        return roomName.get();
    }

    public SimpleStringProperty roomNameProperty() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName.set(roomName);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
