package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseRepository {
    void addCourse(Course course) throws SQLException;
    List<Course> readAll() throws SQLException;

}
