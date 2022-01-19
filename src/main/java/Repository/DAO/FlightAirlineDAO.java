package Repository.DAO;

import Model.Facility;
import Model.FlightAirline;
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

public class FlightAirlineDAO implements Repository<FlightAirline, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    FlightAirlineDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[FlightAirline]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[FlightAirline] where FlightAirlineID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[FlightAirline] where FlightAirlineID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[FlightAirline]([FlightAirlineID],[FlightID],[AirlineID]) values(?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[FlightAirline] set FlightID = ? , AirlineID = ? where FlightAirlineID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public FlightAirline findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                FlightAirline flightAirline = new FlightAirline();
                flightAirline.setFlightId(result.getInt("FlightID"));
                flightAirline.setAirlineId(result.getInt("AirlineID"));
                flightAirline.setId(result.getLong(1));
                return flightAirline;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FlightAirline> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<FlightAirline> flightAirlines = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    FlightAirline flightAirline = new FlightAirline();
                    flightAirline.setFlightId(result.getInt("FlightID"));
                    flightAirline.setAirlineId(result.getInt("AirlineID"));
                    flightAirline.setId(result.getLong(1));
                    flightAirlines.add(flightAirline);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flightAirlines;
    }

    @Override
    public List<FlightAirline> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<FlightAirline> flightAirlines = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                FlightAirline flightAirline = new FlightAirline();
                flightAirline.setFlightId(result.getInt("FlightID"));
                flightAirline.setAirlineId(result.getInt("AirlineID"));
                flightAirline.setId(result.getLong(1));
                flightAirlines.add(flightAirline);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flightAirlines;
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
    public FlightAirline save(FlightAirline E) {
        FlightAirline flightAirline  = findById(E.getId());
        if(flightAirline != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setLong(1, E.getFlightId());
                statement.setLong(2, E.getAirlineId());
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
                statement.setLong(2, E.getFlightId());
                statement.setLong(3, E.getAirlineId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
