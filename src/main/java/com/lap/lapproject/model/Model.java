package com.lap.lapproject.model;

import com.calendarfx.model.Entry;
import com.lap.lapproject.repos.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Model {

    private static final Logger log = LoggerFactory.getLogger(Model.class);

    public ArrayList<Booking> bookings = new ArrayList<>();
    public ArrayList<Entry<Booking>> bookingEntries = new ArrayList<>();

    private BookingRepositoryJDBC bookingRepo = new BookingRepositoryJDBC();

    public Model() {
        try {
            bookings.addAll(bookingRepo.readAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringProperty pathForDetailView = new SimpleStringProperty();

    public String getPathForDetailView() {
        return pathForDetailView.get();
    }

    public StringProperty pathForDetailViewProperty() {
        return pathForDetailView;
    }

    public void setPathForDetailView(String pathForDetailView) {
        this.pathForDetailView.set(pathForDetailView);
    }



    private ObjectProperty<User> loggedInUser = new SimpleObjectProperty<>();

    public String getAuthority(){
        return getLoggedInUser() == null ? "guest" :  getLoggedInUser().getAuthority();
    }

    public User getLoggedInUser() {
        return loggedInUser.get();
    }


    public ObjectProperty<User> loggedInUserProperty() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser.set(loggedInUser);
    }



}
