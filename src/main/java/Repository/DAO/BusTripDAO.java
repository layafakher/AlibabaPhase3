package Repository.DAO;

import Model.BusTrip;
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

public class BusTripDAO implements Repository<BusTrip, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    private Connection connection;

    BusTripDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        this.connection = connection;
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[BusTrip]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[BusTrip] where BusTripID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[BusTrip] where BusTripID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[BusTrip]([BusTripID],[FinalStop],[OriginTerminal],[IsNonStop],[BusCompanyID],[IsVip]) values(?,?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[BusTrip] set FinalStop = ? , OriginTerminal = ? , IsNonStop = ? , BusCompanyID = ? , IsVip = ? where BusTripID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BusTrip findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            TripDAO tripDAO = new TripDAO(connection);
            Trip trip = tripDAO.findById(id);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                BusTrip busTrip = new BusTrip();
                busTrip.setFinalStop(result.getString("FinalStop"));
                busTrip.setOriginTerminal(result.getString("OriginTerminal"));
                busTrip.setVip(result.getBoolean("IsVip"));
                busTrip.setNonStop(result.getBoolean("IsNonStop"));
                busTrip.setId(result.getLong(1));
                busTrip.setBusCompanyId(result.getLong(5));
                if(trip != null){
                    busTrip.setOrigin(trip.getOrigin());
                    busTrip.setCapacity(trip.getCapacity());
                    busTrip.setDestination(trip.getDestination());
                    busTrip.setDepartureTime(trip.getDepartureTime());
                }
                return busTrip;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BusTrip> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<BusTrip> busTrips = new ArrayList<>();
        try {
            for (Long id : longs) {
                TripDAO tripDAO = new TripDAO(connection);
                Trip trip = tripDAO.findById(id);
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    BusTrip busTrip = new BusTrip();
                    busTrip.setFinalStop(result.getString("FinalStop"));
                    busTrip.setOriginTerminal(result.getString("OriginTerminal"));
                    busTrip.setVip(result.getBoolean("IsVip"));
                    busTrip.setNonStop(result.getBoolean("IsNonStop"));
                    busTrip.setId(result.getLong(1));
                    busTrip.setBusCompanyId(result.getLong(5));
                    if(trip != null){
                        busTrip.setOrigin(trip.getOrigin());
                        busTrip.setCapacity(trip.getCapacity());
                        busTrip.setDestination(trip.getDestination());
                        busTrip.setDepartureTime(trip.getDepartureTime());
                    }
                    busTrips.add(busTrip);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return busTrips;
    }

    @Override
    public List<BusTrip> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<BusTrip> busTrips = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                TripDAO tripDAO = new TripDAO(connection);
                Trip trip = tripDAO.findById(result.getLong(1));
                BusTrip busTrip = new BusTrip();
                busTrip.setFinalStop(result.getString("FinalStop"));
                busTrip.setOriginTerminal(result.getString("OriginTerminal"));
                busTrip.setVip(result.getBoolean("IsVip"));
                busTrip.setNonStop(result.getBoolean("IsNonStop"));
                busTrip.setId(result.getLong(1));
                busTrip.setBusCompanyId(result.getLong(5));
                if(trip != null){
                    busTrip.setOrigin(trip.getOrigin());
                    busTrip.setCapacity(trip.getCapacity());
                    busTrip.setDestination(trip.getDestination());
                    busTrip.setDepartureTime(trip.getDepartureTime());
                }
                busTrips.add(busTrip);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return busTrips;
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
    public BusTrip save(BusTrip E) {
        BusTrip busTrip  = findById(E.getId());
        TripDAO tripDAO = new TripDAO(connection);
        tripDAO.save(E);
        if(busTrip != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getFinalStop());
                statement.setNString(2,E.getOriginTerminal());
                statement.setBoolean(3,E.isNonStop());
                statement.setLong(4, E.getBusCompanyId());
                statement.setBoolean(5,E.isVip());
                statement.setLong(6, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, E.getId());
                statement.setNString(2,E.getFinalStop());
                statement.setNString(3,E.getOriginTerminal());
                statement.setBoolean(4,E.isNonStop());
                statement.setLong(5, E.getBusCompanyId());
                statement.setBoolean(6,E.isVip());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
