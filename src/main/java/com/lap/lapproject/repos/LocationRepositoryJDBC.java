package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRepositoryJDBC extends Repository implements LocationRepository {


    public boolean getLocation() {
        Connection connection = connect();
        ListModel.locationList.clear();
        String query = "SELECT * FROM location";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Location location = new Location(resultSet.getLong("location_id"), resultSet.getString("street"),
                        resultSet.getString("zip"), resultSet.getString("city"));

                ListModel.locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
