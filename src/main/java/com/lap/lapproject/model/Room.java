package com.lap.lapproject.model;;
import javafx.beans.property.SimpleIntegerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Room {

    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty roomNumber;
    private SimpleIntegerProperty size;
    private Location location;
    private List<Equipment> equipments;


    public Room(int id, int roomNumber, int size, Location location ) {
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

//    @Override
//    public String toString() {
//        return "Room{" +
//                "id=" + id +
//                ", roomNumber=" + roomNumber +
//                ", size=" + size +
//                ", location=" + location +
//                ", equipment=" + equipments.toString() +
//                '}';
//    }


    @Override
    public String toString() {
        return String.valueOf(getRoomNumber());
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

    public List<Equipment> getEquipment() {
        return equipments;
    }

    public void setEquipment(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public String getEquipmentAsString() {
        if (equipments == null) {
            return "";
        }
        return equipments.stream().map(equipment -> equipment.getDescription()).collect(Collectors.joining(", "));
    }
}
