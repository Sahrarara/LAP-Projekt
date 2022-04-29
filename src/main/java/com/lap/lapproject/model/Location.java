package com.lap.lapproject.model;

import javafx.beans.property.SimpleStringProperty;

public class Location {
    private long id;
    private SimpleStringProperty street;
    private SimpleStringProperty zipcode;
    private SimpleStringProperty city;

    public Location(long id, String street, String zipcode, String city) {
        this.id = id;
        this.street = new SimpleStringProperty(street);
        this.zipcode = new SimpleStringProperty(zipcode);
        this.city = new SimpleStringProperty(city);
    }

    public Location(String street) {
        this.street = new SimpleStringProperty(street);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getZipcode() {
        return zipcode.get();
    }

    public SimpleStringProperty zipcodeProperty() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode.set(zipcode);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }
}
