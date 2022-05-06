package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;

import java.sql.*;

import static com.lap.lapproject.model.ListModel.programList;

public class ProgramRepositoryJDBC extends Repository implements ProgramRepository {
    //TODO: Priebsch fragen warum Fehler
    //private static final String SQL_INSERT_PROGRAM = "INSERT INTO programs(program_id, name) VALUES (?, ?)";
    private static final String ADD_NEW_PROGRAM_SQL_STRING = "INSERT INTO programs (name) VALUES (?)";
    private static final String SELECT_PROGRAM_SQL_STRING = "SELECT * FROM programs";
    private static final String GET_PROGRAM_ID_BY_PROGRAM_NAME_SQL_STRING = "SELECT program_id FROM programs WHERE name = (?)";



    @Override
    public void getAllPrograms() {
        Connection connection = connect();
        ListModel.programList.clear();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_PROGRAM_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Program program = new Program(resultSet.getLong("program_id"), resultSet.getString("name"));
                programList.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CREATE
    @Override
    public boolean getProgram() throws SQLException {
        Connection connection = connect();
        ListModel.programList.clear();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_PROGRAM_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Program program = new Program(resultSet.getLong("program_id"), resultSet.getString("name"));
                ListModel.programList.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void addProgram(Program program) throws SQLException {
        Connection connection = connect();
        ListModel.programList.clear();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_PROGRAM_SQL_STRING, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, program.getProgramName());


            preparedStatement.executeUpdate();

        }
    }

    //TODO: Update und Delete machen!
    //UPDATE
    @Override
    public void updateProgram(Program program) throws SQLException {

    }

    //DELETE
    @Override
    public void deleteProgram(Program program) throws SQLException {

    }


    @Override
    public int getProgramIdByProgramName(String programName) {

        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int programId = 0;

        try {
            preparedStatement = connection.prepareStatement(GET_PROGRAM_ID_BY_PROGRAM_NAME_SQL_STRING);
            preparedStatement.setString(1, programName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                programId = resultSet.getInt("program_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programId;
    }

}
