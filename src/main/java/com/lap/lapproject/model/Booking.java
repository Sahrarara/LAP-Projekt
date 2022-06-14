package com.lap.lapproject.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Booking {

    private static final Logger log = LoggerFactory.getLogger(Booking.class);

    private int id;
    private Room room;
    private Trainer trainer;
    private Course course;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private String recurrenceRule;
    private User user;
    private Location location;



    public Booking(int id, Room room, Trainer trainer,  User user, Course course, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String recurrenceRule) {
        this.id = id;
        this.room = room;
        this.trainer = trainer;
        this.user = user;
        this.course = course;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.recurrenceRule = recurrenceRule;
    }

    public Booking(Room room, Trainer trainer, User user,Course course, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String recurrenceRule) {
        this.room = room;
        this.trainer = trainer;
        this.user = user;
        this.course = course;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.recurrenceRule = recurrenceRule;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
