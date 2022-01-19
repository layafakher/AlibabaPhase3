package Repository.DAO;

import Model.Airport;
import Model.Trip;
import Repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

import static Repository.DAO.StatementType.*;

public class TripDAO implements Repository<Trip, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    TripDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Trip]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Trip] where TripID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Trip] where TripID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Trip]([TripID],[Origin],[Destination],[DepartureTime],[Capacity]) values(?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Trip] set Origin = ? , Destination = ? , DepartureTime = ? , Capacity = ? where TripID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Trip findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Trip trip = new Trip();
                trip.setOrigin(result.getString("Origin"));
                trip.setDestination(result.getString("Destination"));
                trip.setDepartureTime(result.getDate("DepartureTime"));
                trip.setCapacity(result.getInt("Capacity"));
                trip.setId(result.getLong(1));
                return trip;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Trip> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Trip> trips = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    Trip trip = new Trip();
                    trip.setOrigin(result.getString("Origin"));
                    trip.setDestination(result.getString("Destination"));
                    trip.setDepartureTime(result.getDate("DepartureTime"));
                    trip.setCapacity(result.getInt("Capacity"));
                    trip.setId(result.getLong(1));
                    trips.add(trip);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trips;
    }

    @Override
    public List<Trip> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Trip> trips = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                Trip trip = new Trip();
                trip.setOrigin(result.getString("Origin"));
                trip.setDestination(result.getString("Destination"));
                trip.setDepartureTime(result.getDate("DepartureTime"));
                trip.setCapacity(result.getInt("Capacity"));
                trip.setId(result.getLong(1));
                trips.add(trip);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return trips;
    }

    @Override
    public Boolean deleteByID(Long id) {
        PreparedStatement statement = statements.get(DELETE_BY_ID);
        try {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Boolean.FALSE;
        }
    }


    @Override
    public Boolean DeleteByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(DELETE_BY_ID);
        int temp;
        boolean result = true;
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                temp = statement.executeUpdate();
                if(temp == 0){
                    result = false;
                }
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Trip save(Trip E) {
        Trip trip  = findById((Long) E.getId());
        if(trip != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getOrigin());
                statement.setNString(2,E.getDestination());
                statement.setDate(3,new java.sql.Date(E.getDepartureTime().getTime()));
                statement.setInt(4,E.getCapacity());
                statement.setLong(5, (Long) E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, (Long) E.getId());
                statement.setNString(2,E.getOrigin());
                statement.setNString(3,E.getDestination());
                statement.setDate(4,new java.sql.Date(E.getDepartureTime().getTime()));
                statement.setInt(5,E.getCapacity());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById((Long) E.getId());
    }
}
