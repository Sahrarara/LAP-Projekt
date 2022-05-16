package com.lap.lapproject.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


public class Course {

    private static final Logger log = LoggerFactory.getLogger(Course.class);

    private SimpleIntegerProperty id;
    private SimpleStringProperty courseName;
    private Program program;
    private LocalDate courseStart;
    private LocalDate courseEnd;
    private SimpleIntegerProperty groupSize;

    public Course(int id, String courseName, Program program, LocalDate courseStart, LocalDate courseEnd,
                  int groupSize) {
        this.id = new SimpleIntegerProperty(id);
        this.courseName = new SimpleStringProperty(courseName);
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = new SimpleIntegerProperty(groupSize);
    }
    public Course(String courseName, Program program, LocalDate courseStart, LocalDate courseEnd,
                  int groupSize) {
        this.courseName = new SimpleStringProperty(courseName);
        this.program = program;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.groupSize = new SimpleIntegerProperty(groupSize);
        this.id = new SimpleIntegerProperty();
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

    public int getGroupSize() {return groupSize.get();
    }

    public SimpleIntegerProperty groupSizeProperty() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize.set(groupSize);
    }

    public LocalDate getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(LocalDate courseStart) {
        this.courseStart = courseStart;
    }

    public LocalDate getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(LocalDate courseEnd) {
        this.courseEnd = courseEnd;
    }

    @Override
    public String toString() {
        return getCourseName();
    }
}
