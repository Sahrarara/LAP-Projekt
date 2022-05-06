package com.lap.lapproject.model;

import com.calendarfx.model.Entry;
import com.lap.lapproject.repos.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

public class Model {

    private static final Logger log = LoggerFactory.getLogger(Model.class);

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
            //courseRepositoryJDBC.addCourse();
            courseRepositoryJDBC.getCourse();
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
