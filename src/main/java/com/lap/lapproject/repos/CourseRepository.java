package com.lap.lapproject.repos;

import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.Equipment;

import java.sql.SQLException;
import java.util.List;

/**
 * Das CourseRepository Interface erstellt die leeren Methoden addCourse(), deleteCourse(), updateCourse(), getCoursesCountByProgramId(), getCourseCountByCourseName() und die ListMethode readAll(),
 * diese Methoden müssen in der Klasse die dieses Interface implementiert definiert werden.
 */
public interface CourseRepository {
    int addCourse(Course course) throws SQLException;

    List<Course> readAll() throws SQLException;

    void deleteCourse(Course course) throws SQLException;

    void updateCourse(Course course) throws SQLException;

    int getCoursesCountByProgramId(int programId) throws SQLException;

    int getCourseCountByCourseName(String courseName) throws SQLException;
}
