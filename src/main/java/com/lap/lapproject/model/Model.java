package com.lap.lapproject.model;

import com.calendarfx.model.Entry;
import com.lap.lapproject.repos.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Collection;

public class Model {
    public ArrayList<Booking> bookings = new ArrayList<>();
    public ArrayList<Entry<Booking>> bookingEntries = new ArrayList<>();

    private BookingRepositoryJDBC bookingRepo = new BookingRepositoryJDBC();
    private UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
    private EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    private CourseRepositoryJDBC  courseRepositoryJDBC = new CourseRepositoryJDBC();
    public Model(){
        try {
            bookings.addAll(bookingRepo.readAll());
            userRepositoryJDBC.getTrainer();
            equipmentRepositoryJDBC.getEquipment();
            courseRepositoryJDBC.addCourse();
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
