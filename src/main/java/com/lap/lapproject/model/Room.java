package com.lap.lapproject.model;;
import javafx.beans.property.SimpleIntegerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Room {

    private static final Logger log = LoggerFactory.getLogger(Room.class);

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty roomNumber;
    private SimpleIntegerProperty size;
    private Location location;
    private byte[] photo;
    private Equipment equipment;


    public Room(int id, int roomNumber, int size, Location location ) {
        this.id = new SimpleIntegerProperty(id);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
    }


    public Room(int id, int roomNumber, int size, Location location, Equipment equipment) {
        this.id = new SimpleIntegerProperty(id);
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
        this.equipment = equipment;
    }

    public Room(int roomNumber) {
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
    }

    //TODO: photo, equipment
    public Room(int roomNumber, int size, Location location, byte[] photo, Equipment equipment) {
        this.roomNumber = new SimpleIntegerProperty(size);
        this.size = new SimpleIntegerProperty(size);
        this.location = location;
        this.photo = photo;
        this.equipment = equipment;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    @Override
    public String toString() {
        return String.valueOf(getRoomNumber());
    }
}
