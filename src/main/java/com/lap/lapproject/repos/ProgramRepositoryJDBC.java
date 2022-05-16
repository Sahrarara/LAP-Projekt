package com.lap.lapproject.repos;

import com.lap.lapproject.model.Program;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProgramRepositoryJDBC extends Repository implements ProgramRepository {

    private static final String ADD_NEW_PROGRAM_SQL_STRING = "INSERT INTO programs (name) VALUES (?)";
    private static final String SELECT_PROGRAM_SQL_STRING = "SELECT * FROM programs";
    private static final String GET_PROGRAM_BY_PROGRAM_NAME_SQL_STRING = "SELECT * FROM programs WHERE name = (?)";
    private static final String UPDATE_PROGRAM_SQL_STRING = "UPDATE programs SET name =? WHERE program_id=?";


    private static final String DELETE_PROGRAM_SQL_STRING =
            "DELETE FROM programs WHERE program_id=?" /* + "UPDATE rooms_equipment SET equipment_id = null WHERE program_id=?"*/;


    @Override
    public List<Program> readProgram() throws SQLException {
        Connection connection = connect();
        List<Program> programList = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_PROGRAM_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Program program = new Program(resultSet.getInt("program_id"), resultSet.getString("name"));
                programList.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programList;
    }


    public int addProgram(Program program) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int generatedKey = 0;

        try {
            preparedStatement = connection.prepareStatement(ADD_NEW_PROGRAM_SQL_STRING, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, program.getProgramName());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next() ) {
                generatedKey = resultSet.getInt(1);
                program.setId(generatedKey);
            }
            
        } catch (SQLException e ) {
            e.printStackTrace();
        }

        return generatedKey;
    }


    @Override
    public void updateProgram(Program program) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_PROGRAM_SQL_STRING);
            preparedStatement.setString(1, program.getProgramName());
            preparedStatement.setLong(2, program.getId());
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteProgram(Program program) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_PROGRAM_SQL_STRING);
            preparedStatement.setInt(1, program.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Program getProgramByProgramName(String programName) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Program program = null;
        try {
            preparedStatement = connection.prepareStatement(GET_PROGRAM_BY_PROGRAM_NAME_SQL_STRING);
            preparedStatement.setString(1, programName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                program = new Program(resultSet.getInt("program_id"), resultSet.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return program;
    }

}
