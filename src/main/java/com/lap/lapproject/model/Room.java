package com.lap.lapproject.model;

public class Room {
    private String roomName;
    private int size;
    private Location location;
    private byte[] photo;
    private Equipment equipment;


    public Room(String roomName, int size, Location location, byte[] photo, Equipment equipment) {
        this.roomName = roomName;
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
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
