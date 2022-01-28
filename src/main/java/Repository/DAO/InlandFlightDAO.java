package Repository.DAO;

import Model.Flight;
import Model.InlandFlight;
import Repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static Repository.DAO.StatementType.*;

public class InlandFlightDAO implements Repository<InlandFlight, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    private Connection connection;

    InlandFlightDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        this.connection = connection;
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[InlandFlight]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[InlandFlight] where InlandFlightID = ?"
            ));
            statements.put(FIND_BY_DATA, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[InlandFlight] INNER JOIN [Alibaba].[dbo].[Flight] ON [Alibaba].[dbo].[InlandFlight].[InlandFlightID]=[Alibaba].[dbo].[Flight].[FlightID] INNER JOIN [Alibaba].[dbo].[Trip] ON [Alibaba].[dbo].[Trip].[TripID] = [Alibaba].[dbo].[Flight].[FlightID] where Origin = ? AND Destination = ? AND IsOneWay = ? "
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[InlandFlight] where InlandFlightID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[InlandFlight]([InlandFlightID]) values(?)"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public InlandFlight findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            FlightDAO flightDAO = new FlightDAO(connection);
            Flight flight = flightDAO.findById(id);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                InlandFlight inlandFlight = new InlandFlight();
                inlandFlight.setId(result.getLong(1));
                if(flight != null){
                    inlandFlight.setTicketType(flight.getTicketType());
                    inlandFlight.setOneWay(flight.isOneWay());
                    inlandFlight.setArrivalTime(flight.getArrivalTime());
                    inlandFlight.setReturnFlightId(flight.getReturnFlightId());
                    inlandFlight.setOrigin(flight.getOrigin());
                    inlandFlight.setCapacity(flight.getCapacity());
                    inlandFlight.setDestination(flight.getDestination());
                    inlandFlight.setDepartureTime(flight.getDepartureTime());
                }
                return inlandFlight;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<InlandFlight> findByData(String origin, String destination, boolean type){
        PreparedStatement statement = statements.get(FIND_BY_DATA);
        List<InlandFlight> inlandFlights = new LinkedList<>();
        try {
            statement.setNString(1, origin);
            statement.setNString(2, destination);
            statement.setNString(3, String.valueOf(type));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                inlandFlights.add(findById(result.getLong(1)));
            }
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return inlandFlights;
    }

    @Override
    public List<InlandFlight> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<InlandFlight> inlandFlights = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    FlightDAO flightDAO = new FlightDAO(connection);
                    Flight flight = flightDAO.findById(id);
                    InlandFlight inlandFlight = new InlandFlight();
                    inlandFlight.setId(result.getLong(1));
                    if(flight != null){
                        inlandFlight.setTicketType(flight.getTicketType());
                        inlandFlight.setOneWay(flight.isOneWay());
                        inlandFlight.setArrivalTime(flight.getArrivalTime());
                        inlandFlight.setReturnFlightId(flight.getReturnFlightId());
                        inlandFlight.setOrigin(flight.getOrigin());
                        inlandFlight.setCapacity(flight.getCapacity());
                        inlandFlight.setDestination(flight.getDestination());
                        inlandFlight.setDepartureTime(flight.getDepartureTime());
                    }
                    inlandFlights.add(inlandFlight);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return inlandFlights;
    }

    @Override
    public List<InlandFlight> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<InlandFlight> inlandFlights = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                FlightDAO flightDAO = new FlightDAO(connection);
                Flight flight = flightDAO.findById(result.getLong(1));
                InlandFlight inlandFlight = new InlandFlight();
                inlandFlight.setId(result.getLong(1));
                if(flight != null){
                    inlandFlight.setTicketType(flight.getTicketType());
                    inlandFlight.setOneWay(flight.isOneWay());
                    inlandFlight.setArrivalTime(flight.getArrivalTime());
                    inlandFlight.setReturnFlightId(flight.getReturnFlightId());
                    inlandFlight.setOrigin(flight.getOrigin());
                    inlandFlight.setCapacity(flight.getCapacity());
                    inlandFlight.setDestination(flight.getDestination());
                    inlandFlight.setDepartureTime(flight.getDepartureTime());
                }
                inlandFlights.add(inlandFlight);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return inlandFlights;
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
    public InlandFlight save(InlandFlight E) {
        InlandFlight inlandFlight  = findById(E.getId());
        FlightDAO flightDAO = new FlightDAO(connection);
        flightDAO.save(E);
        if(inlandFlight == null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setLong(1, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
