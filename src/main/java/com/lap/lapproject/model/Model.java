package com.lap.lapproject.model;

import com.calendarfx.model.Entry;
import com.lap.lapproject.repos.BookingRepositoryJDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collection;

public class Model {
    public ArrayList<Booking> bookings = new ArrayList<>();
    public ArrayList<Entry<Booking>> bookingEntries = new ArrayList<>();

    private BookingRepositoryJDBC bookingRepo = new BookingRepositoryJDBC();
    public Model(){
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
}
