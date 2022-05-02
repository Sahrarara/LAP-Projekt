package com.lap.lapproject.repos;

import com.lap.lapproject.model.ListModel;
import com.lap.lapproject.model.Location;

import java.sql.*;

public class LocationRepositoryJDBC extends Repository implements LocationRepository {

    private static final String ADD_NEW_LOCATION_SQL_STRING = "INSERT INTO location (street, zip, city) VALUES (?, ?, ?)";

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
    @Override
    public void addLocation(Location location) throws SQLException {
        Connection connection = connect();

        try(PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_LOCATION_SQL_STRING, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, location.getStreet());
            preparedStatement.setString(2, location.getZipcode());
            preparedStatement.setString(3, location.getCity());

            preparedStatement.executeUpdate();

            /*
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                while(resultSet.next()){
                    //long locationId = resultSet.getLong("location_id");
                    //location.setId(locationId);

                }
            }
             */

        }
    }

}

