package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Course {

    private static final Logger log = LoggerFactory.getLogger(Course.class);

    private SimpleIntegerProperty id;
    private SimpleStringProperty courseName;
    private Program program;
    private LocalDateTime courseStart;
    private LocalDateTime courseEnd;
    private SimpleIntegerProperty groupSize;

    public Course(int id, String courseName, Program program, LocalDateTime courseStart, LocalDateTime courseEnd,
                  int groupSize) {
        this.id = new SimpleIntegerProperty(id);
        this.courseName = new SimpleStringProperty(courseName);
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = new SimpleIntegerProperty(groupSize);
    }
    public Course(String courseName, Program program, LocalDateTime courseStart, LocalDateTime courseEnd,
                  int groupSize) {
        this.courseName = new SimpleStringProperty(courseName);
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = new SimpleIntegerProperty(groupSize);
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

    public String getCourseName() {
        return courseName.get();
    }

    public SimpleStringProperty courseNameProperty() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }


    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public int getGroupSize() {
        return groupSize.get();
    }

    public SimpleIntegerProperty groupSizeProperty() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize.set(groupSize);
    }

    public LocalDateTime getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(LocalDateTime courseStart) {
        this.courseStart = courseStart;
    }

    public LocalDateTime getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDateTime courseEnd) {
        this.courseEnd = courseEnd;
    }

    @Override
    public String toString() {
        return getCourseName();
    }
}
