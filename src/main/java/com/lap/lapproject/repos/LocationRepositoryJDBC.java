package com.lap.lapproject.repos;
import com.lap.lapproject.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationRepositoryJDBC extends Repository implements LocationRepository {

    private static final String ADD_NEW_LOCATION_SQL_STRING = "INSERT INTO location (street, zip, city) VALUES (?, ?, ?)";
    private static final String SELECT_LOCATION_SQL_STRING = "SELECT * FROM location";

    public List<Location> readAll() {
        Connection connection = connect();
        List<Location> locationList = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(SELECT_LOCATION_SQL_STRING);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Location location = new Location(resultSet.getInt("location_id"), resultSet.getString("street"),
                        resultSet.getString("zip"), resultSet.getString("city"));

               locationList.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationList;
    }
    @Override
    public void addLocation(Location location) throws SQLException {
        Connection connection = connect();

        try(PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_LOCATION_SQL_STRING, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, location.getStreet());
            preparedStatement.setString(2, location.getZipcode());
            preparedStatement.setString(3, location.getCity());

            preparedStatement.executeUpdate();

        }
    }

}

