package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramRepositoryJDBC extends Repository implements ProgramRepository{
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
                Program program = new Program(resultSet.getString("name"));
                ListModel.programList.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
