package com.lap.lapproject.model;

import java.time.LocalDateTime;

public class Booking {
    private Room room;
    private Trainer trainer;
    private Course course;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;


    public Booking(Room room, Trainer trainer, Course course, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd) {
        this.room = room;
        this.trainer = trainer;
        this.course = course;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
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
}
