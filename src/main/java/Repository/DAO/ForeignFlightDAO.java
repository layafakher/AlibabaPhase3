package Repository.DAO;

import Model.Flight;
import Model.ForeignFlight;
import Model.Hotel;
import Repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static Repository.DAO.StatementType.*;

public class ForeignFlightDAO implements Repository<ForeignFlight, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    private Connection connection;

    ForeignFlightDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        this.connection = connection;
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[ForeignFlight]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[ForeignFlight] where ForeignFlightID = ?"
            ));
            statements.put(FIND_BY_DATA, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[ForeignFlight] INNER JOIN [Alibaba].[dbo].[Flight] ON [Alibaba].[dbo].[ForeignFlight].[ForeignFlightID]=[Alibaba].[dbo].[Flight].[FlightID] INNER JOIN [Alibaba].[dbo].[Trip] ON [Alibaba].[dbo].[Trip].[TripID] = [Alibaba].[dbo].[Flight].[FlightID] where Origin = ? AND Destination = ? AND IsOneWay = ? AND TicketType = ? "
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[ForeignFlight] where ForeignFlightID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[ForeignFlight]([ForeignFlightID],[FlightDuration],[NumberOfStops]) values(?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[ForeignFlight] set FlightDuration = ? , NumberOfStops = ? where ForeignFlightID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ForeignFlight findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            FlightDAO flightDAO = new FlightDAO(connection);
            Flight flight = flightDAO.findById(id);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                ForeignFlight foreignFlight = new ForeignFlight();
                foreignFlight.setFlightDuration(result.getDouble("FlightDuration"));
                foreignFlight.setNumberOfStops(result.getInt("NumberOfStops"));
                foreignFlight.setId(result.getLong(1));
                if(flight != null){
                    foreignFlight.setTicketType(flight.getTicketType());
                    foreignFlight.setOneWay(flight.isOneWay());
                    foreignFlight.setArrivalTime(flight.getArrivalTime());
                    foreignFlight.setReturnFlightId(flight.getReturnFlightId());
                    foreignFlight.setOrigin(flight.getOrigin());
                    foreignFlight.setCapacity(flight.getCapacity());
                    foreignFlight.setDestination(flight.getDestination());
                    foreignFlight.setDepartureTime(flight.getDepartureTime());
                }
                return foreignFlight;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<ForeignFlight> findByData(String origin,String destination,boolean type,String economy){
        PreparedStatement statement = statements.get(FIND_BY_DATA);
        List<ForeignFlight> foreignFlights = new LinkedList<>();
        try {
            statement.setNString(1, origin);
            statement.setNString(2, destination);
            statement.setNString(3, String.valueOf(type));
            statement.setNString(4, economy);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                foreignFlights.add(findById(result.getLong(1)));
            }
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return foreignFlights;
    }

    @Override
    public List<ForeignFlight> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<ForeignFlight> foreignFlights = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    FlightDAO flightDAO = new FlightDAO(connection);
                    Flight flight = flightDAO.findById(id);
                    ForeignFlight foreignFlight = new ForeignFlight();
                    foreignFlight.setFlightDuration(result.getDouble("FlightDuration"));
                    foreignFlight.setNumberOfStops(result.getInt("NumberOfStops"));
                    foreignFlight.setId(result.getLong(1));
                    if(flight != null){
                        foreignFlight.setTicketType(flight.getTicketType());
                        foreignFlight.setOneWay(flight.isOneWay());
                        foreignFlight.setArrivalTime(flight.getArrivalTime());
                        foreignFlight.setReturnFlightId(flight.getReturnFlightId());
                        foreignFlight.setOrigin(flight.getOrigin());
                        foreignFlight.setCapacity(flight.getCapacity());
                        foreignFlight.setDestination(flight.getDestination());
                        foreignFlight.setDepartureTime(flight.getDepartureTime());
                    }
                    foreignFlights.add(foreignFlight);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return foreignFlights;
    }

    @Override
    public List<ForeignFlight> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<ForeignFlight> foreignFlights = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                FlightDAO flightDAO = new FlightDAO(connection);
                Flight flight = flightDAO.findById(result.getLong(1));
                ForeignFlight foreignFlight = new ForeignFlight();
                foreignFlight.setFlightDuration(result.getDouble("FlightDuration"));
                foreignFlight.setNumberOfStops(result.getInt("NumberOfStops"));
                foreignFlight.setId(result.getLong(1));
                if(flight != null){
                    foreignFlight.setTicketType(flight.getTicketType());
                    foreignFlight.setOneWay(flight.isOneWay());
                    foreignFlight.setArrivalTime(flight.getArrivalTime());
                    foreignFlight.setReturnFlightId(flight.getReturnFlightId());
                    foreignFlight.setOrigin(flight.getOrigin());
                    foreignFlight.setCapacity(flight.getCapacity());
                    foreignFlight.setDestination(flight.getDestination());
                    foreignFlight.setDepartureTime(flight.getDepartureTime());
                }
                foreignFlights.add(foreignFlight);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return foreignFlights;
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
    public ForeignFlight save(ForeignFlight E) {
        ForeignFlight foreignFlight  = findById(E.getId());
        FlightDAO flightDAO = new FlightDAO(connection);
        flightDAO.save(E);
        if(foreignFlight != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setDouble(1,E.getFlightDuration());
                statement.setInt(2,E.getNumberOfStops());
                statement.setLong(3, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, E.getId());
                statement.setDouble(2,E.getFlightDuration());
                statement.setInt(3,E.getNumberOfStops());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
