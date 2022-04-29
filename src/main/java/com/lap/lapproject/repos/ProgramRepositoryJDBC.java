package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

public class ProgramRepositoryJDBC extends Repository implements ProgramRepository {
    //TODO: Priebsch fragen warum Fehler
    //private static final String SQL_INSERT_PROGRAM = "INSERT INTO programs(program_id, name) VALUES (?, ?)";
    private static final String SQL_INSERT_PROGRAM_NOID = "INSERT INTO program(name) VALUES (?)";

    @Override
    public void addProgram(Program program) throws SQLException {
        Connection connection = connect();

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PROGRAM_NOID, Statement.RETURN_GENERATED_KEYS)){
            //preparedStatement.setLong(1, program.getId());
            preparedStatement.setString(2, program.getProgram());

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                while(resultSet.next()){
                    //long userId = resultSet.getLong("program_id");
                    //program.setId(userId);
                    String programName = resultSet.getString("name");
                    program.setProgram(programName);
                }
            }
        }
    }


    @Override
    public boolean getProgram() throws SQLException {
        Connection connection = connect();
        ListModel.programList.clear();
        String query = "SELECT * FROM programs";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
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
}
