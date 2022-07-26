package com.lap.lapproject.repos;
import com.lap.lapproject.model.Location;
import com.lap.lapproject.model.Program;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationRepositoryJDBC extends Repository implements LocationRepository {

    private static final String ADD_NEW_LOCATION_SQL_STRING = "INSERT INTO location (street, zip, city) VALUES (?, ?, ?)";
    private static final String SELECT_LOCATION_SQL_STRING = "SELECT * FROM location";
    private static final String DELETE_LOCATION_SQL_STRING = "DELETE FROM location WHERE location_id=?";
    private static final String UPDATE_LOCATION_SQL_STRING = "UPDATE location SET street=?, zip=?, city=? WHERE location_id=?";


    static {
        logger = LoggerFactory.getLogger(LocationRepository.class);
    }

    @Override
    public List<Location> readAll() throws SQLException {
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
        } finally {
            if (connection != null) connection.close();
        }
        return locationList;
    }


    @Override
    public int addLocation(Location location) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int generatedKey = 0;

        try{
            preparedStatement = connection.prepareStatement(ADD_NEW_LOCATION_SQL_STRING, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, location.getStreet());
            preparedStatement.setString(2, location.getZipcode());
            preparedStatement.setString(3, location.getCity());

            //preparedStatement.executeUpdate();
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next() ) {
                generatedKey = resultSet.getInt(1);
               location.setId(generatedKey);
            }

        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }

        return generatedKey;
        }


    @Override
    public void updateLocation(Location location) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_LOCATION_SQL_STRING);
            preparedStatement.setString(1, location.getStreet());
            preparedStatement.setString(2, location.getZipcode());
            preparedStatement.setString(3, location.getCity());
            preparedStatement.setLong(4, location.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Override
    public void deleteLocation(Location location) throws SQLException {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_LOCATION_SQL_STRING);
            preparedStatement.setInt(1, location.getId());
            preparedStatement.executeQuery();
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }

}
