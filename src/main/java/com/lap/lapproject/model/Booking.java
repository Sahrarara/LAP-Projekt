package com.lap.lapproject.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Booking {

    private static final Logger log = LoggerFactory.getLogger(Booking.class);

    private Room room;
    private Trainer trainer;
    private Course course;
    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;
    private String recurrenceRule;
    private User user;
    private int bookingID;
    private int roomID;
    private int userID;
    private int trainerID;
    private int courseID;



    public Booking(Room room, Trainer trainer, Course course, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String recurrenceRule) {
        this.room = room;
        this.trainer = trainer;
        this.course = course;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.recurrenceRule = recurrenceRule;
    }

    public Booking(int roomID, int userID, int trainerID, int courseID, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String recurrenceRule) {
        this.roomID = roomID;
        this.userID = userID;
        this.trainerID = trainerID;
        this.courseID = courseID;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.recurrenceRule = recurrenceRule;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
}
