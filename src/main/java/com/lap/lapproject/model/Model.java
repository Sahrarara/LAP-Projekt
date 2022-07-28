package com.lap.lapproject.model;

import com.calendarfx.model.Entry;
import com.lap.lapproject.repos.*;
import com.lap.lapproject.utility.UsabilityMethods;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.Book;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Model {

    private static final Logger log = LoggerFactory.getLogger(Model.class);
    public ArrayList<Booking> bookings = new ArrayList<>();
    public ArrayList<Course> courses = new ArrayList<>();
    public ArrayList<Entry<Booking>> bookingEntries = new ArrayList<>();

    private BookingRepositoryJDBC bookingRepo = new BookingRepositoryJDBC();
    private CourseRepositoryJDBC courseRepo = new CourseRepositoryJDBC();

    public Model() {
        try {
            //TODO: fix me, delete bookings because not used
            bookings.addAll(bookingRepo.readAll());
            courses.addAll(courseRepo.readAll());
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

    public String getAuthority() {
        return getLoggedInUser() == null ? "guest" : getLoggedInUser().getAuthority();
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
