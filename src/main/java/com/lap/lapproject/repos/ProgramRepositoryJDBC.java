package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;

import java.sql.*;

public class ProgramRepositoryJDBC extends Repository implements ProgramRepository {
    //TODO: Priebsch fragen warum Fehler
    //private static final String SQL_INSERT_PROGRAM = "INSERT INTO programs(program_id, name) VALUES (?, ?)";
    private static final String ADD_NEW_PROGRAM_SQL_STRING = "INSERT INTO programs (name) VALUES (?)";
    private static final String SELECT_PROGRAM_SQL_STRING = "SELECT * FROM programs";

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

}
