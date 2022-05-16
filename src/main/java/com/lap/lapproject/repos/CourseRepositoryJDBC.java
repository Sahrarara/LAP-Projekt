package com.lap.lapproject.repos;
import com.lap.lapproject.model.Course;
import com.lap.lapproject.model.Program;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryJDBC extends Repository implements CourseRepository {
   private static final String SELECT_COURSE_SQL_STRING = "SELECT course_id, course_name, course_start, course_end," +
            "group_size, name FROM courses JOIN programs WHERE courses.program_id=programs.program_id";
   private static final String ADD_NEW_COURSE_SQL_STRING = "INSERT INTO courses (course_name, course_start, course_end, program_id, group_size) VALUES (?, ?, ?, ?, ?)";
   private static final String DELETE_COURSE_SQL_STRING = "DELETE FROM courses WHERE course_id=?";
   private static final String UPDATE_COURSE_SQL_STRING = "UPDATE courses SET course_name =?, course_start=?, course_end=?, program_id=?, group_size=? WHERE course_id=?";
   private static final String GET_COURSES_COUNT_BY_PROGRAM_ID_SQL_STRING = "SELECT COUNT(*) AS courses_count FROM courses WHERE program_id = (?)";



    @Override
    public List<Course> readAll() {
        Connection connection = connect();
        List<Course> courses = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL_STRING);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String timeStart = resultSet.getString("course_start");
                LocalDate courseStart = LocalDate.parse(timeStart);
                String timeEnd = resultSet.getString("course_end");
                LocalDate courseEnd = LocalDate.parse(timeEnd);


                Course course = new Course(resultSet.getInt("course_id"), resultSet.getString("course_name"),
                        new Program(resultSet.getString("name")), courseStart, courseEnd, resultSet.getInt("group_size"));
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }


    @Override
    public int addCourse(Course course) throws SQLException {

        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int generatedKey = 0;

        try{
            preparedStatement = connection.prepareStatement(ADD_NEW_COURSE_SQL_STRING, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(course.getCourseStart().atStartOfDay().toLocalDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(course.getCourseEnd().atStartOfDay().toLocalDate()));
            preparedStatement.setInt(4, (int) course.getProgram().getId());
            preparedStatement.setInt(5, course.getGroupSize());

            //preparedStatement.executeUpdate();
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next() ) {
                generatedKey = resultSet.getInt(1);
                course.setId(generatedKey);
            }

        } catch (SQLException e ) {
            e.printStackTrace();
        }

        return generatedKey;
    }

    @Override
    public void updateCourse(Course course) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_COURSE_SQL_STRING);
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(course.getCourseStart().atStartOfDay().toLocalDate()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(course.getCourseEnd().atStartOfDay().toLocalDate()));
            preparedStatement.setInt(4, (int) course.getProgram().getId());
            preparedStatement.setInt(5, course.getGroupSize());
            preparedStatement.setLong(6, course.getId());
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCourse(Course course) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_COURSE_SQL_STRING);
            preparedStatement.setInt(1, course.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCoursesCountByProgramId(int programId) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int coursesCount = 0;
        try {
            preparedStatement = connection.prepareStatement(GET_COURSES_COUNT_BY_PROGRAM_ID_SQL_STRING );
            preparedStatement.setInt(1, programId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coursesCount = resultSet.getInt("courses_count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesCount;
    }
}
