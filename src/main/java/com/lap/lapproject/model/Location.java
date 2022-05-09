package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Location {

    private static final Logger log = LoggerFactory.getLogger(Location.class);

    private SimpleIntegerProperty id;
    private SimpleStringProperty street;
    private SimpleStringProperty zipcode;
    private SimpleStringProperty city;


    public Location(String street) {
        this.street = new SimpleStringProperty(street);
    }

    public Location(int id, String street, String zipcode, String city) {
        this.id = new SimpleIntegerProperty(id);
        this.street = new SimpleStringProperty(street);
        this.zipcode = new SimpleStringProperty(zipcode);
        this.city = new SimpleStringProperty(city);
    }

    public Location(String street, String zipcode, String city) {
        this.street = new SimpleStringProperty(street);
        this.zipcode = new SimpleStringProperty(zipcode);
        this.city = new SimpleStringProperty(city);
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
