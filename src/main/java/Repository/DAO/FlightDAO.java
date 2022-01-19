package Repository.DAO;

import Model.*;
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

public class FlightDAO implements Repository<Flight, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    private Connection connection;

    FlightDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        this.connection = connection;
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Flight]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Flight] where FlightID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Flight] where FlightID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Flight]([FlightID],[TicketType],[IsOneWay],[ArrivalTime],[ReturnFlightID]) values(?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Flight] set TicketType = ? , IsOneWay = ? , ArrivalTime = ? , ReturnFlightID = ? where FlightID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Flight findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            TripDAO tripDAO = new TripDAO(connection);
            Trip trip = tripDAO.findById(id);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Flight flight = new Flight();
                flight.setArrivalTime(result.getDate("ArrivalTime"));
                flight.setTicketType(result.getString("TicketType"));
                flight.setOneWay(result.getBoolean("IsVip"));
                flight.setReturnFlightId(result.getLong("ReturnFlightID"));
                flight.setId(result.getLong(1));
                if(trip != null){
                    flight.setOrigin(trip.getOrigin());
                    flight.setCapacity(trip.getCapacity());
                    flight.setDestination(trip.getDestination());
                    flight.setDepartureTime(trip.getDepartureTime());
                }
                return flight;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Flight> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Flight> flights = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    TripDAO tripDAO = new TripDAO(connection);
                    Trip trip = tripDAO.findById(id);
                    Flight flight = new Flight();
                    flight.setArrivalTime(result.getDate("ArrivalTime"));
                    flight.setTicketType(result.getString("TicketType"));
                    flight.setOneWay(result.getBoolean("IsVip"));
                    flight.setReturnFlightId(result.getLong("ReturnFlightID"));
                    flight.setId(result.getLong(1));
                    if(trip != null){
                        flight.setOrigin(trip.getOrigin());
                        flight.setCapacity(trip.getCapacity());
                        flight.setDestination(trip.getDestination());
                        flight.setDepartureTime(trip.getDepartureTime());
                    }
                    flights.add(flight);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flights;
    }

    @Override
    public List<Flight> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Flight> flights = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                TripDAO tripDAO = new TripDAO(connection);
                Trip trip = tripDAO.findById(result.getLong(1));
                Flight flight = new Flight();
                flight.setArrivalTime(result.getDate("ArrivalTime"));
                flight.setTicketType(result.getString("TicketType"));
                flight.setOneWay(result.getBoolean("IsVip"));
                flight.setReturnFlightId(result.getLong("ReturnFlightID"));
                flight.setId(result.getLong(1));
                if(trip != null){
                    flight.setOrigin(trip.getOrigin());
                    flight.setCapacity(trip.getCapacity());
                    flight.setDestination(trip.getDestination());
                    flight.setDepartureTime(trip.getDepartureTime());
                }
                flights.add(flight);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flights;
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
    public Flight save(Flight E) {
        Flight flight  = findById((Long) E.getId());
        TripDAO tripDAO = new TripDAO(connection);
        tripDAO.save(E);
        if(flight != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getTicketType());
                statement.setBoolean(2,E.isOneWay());
                statement.setDate(3, new java.sql.Date(E.getArrivalTime().getTime()));
                statement.setLong(4, E.getReturnFlightId());
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
                statement.setNString(2,E.getTicketType());
                statement.setBoolean(3,E.isOneWay());
                statement.setDate(4, new java.sql.Date(E.getArrivalTime().getTime()));
                statement.setLong(5, E.getReturnFlightId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById((Long) E.getId());
    }
}
