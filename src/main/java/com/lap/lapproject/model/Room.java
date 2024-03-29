package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;
import java.util.stream.Collectors;

public class Room {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty roomNumber;
    private SimpleIntegerProperty size;
    private Location location;
    private List<Equipment> equipments;


    public Room(int id, int roomNumber, int size, Location location) {
        this.id = new SimpleIntegerProperty(id);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
    }


    public Room(int id, int roomNumber, int size, Location location, List<Equipment> equipments) {
        this.id = new SimpleIntegerProperty(id);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
        this.equipments = equipments;
    }

    @Override
    public boolean equals (Object r) {
        if(!(r instanceof Room)) {
            return false;
        }
        Room r1 = (Room)r;
        boolean b =  this.id.getValue().equals(r1.id.getValue());
        return b;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getRoomNumber() {
        return roomNumber.get();
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public int getSize() {
        return size.get();
    }

    public String sizeProperty() {
        return String.valueOf(size);
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

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }


    @Override
    public String toString() {
        return String.valueOf(getRoomNumber());
    }

    public String getEquipmentAsString() {
        if (equipments == null) {
            return "";
        }
        return equipments.stream().map(equipment -> equipment.getDescription()).collect(Collectors.joining(", "));
    }
}