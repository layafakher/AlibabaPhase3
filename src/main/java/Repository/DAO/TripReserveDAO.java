package Repository.DAO;

import Model.Transaction;
import Model.TripReserve;
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

public class TripReserveDAO implements Repository<TripReserve, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    TripReserveDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[TripReserve]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[TripReserve] where ReserveID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[TripReserve] where ReserveID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[TripReserve]([ReserveID],[ReserveNumber],[PassengerCount],[UserID],[TripID],[Price],[SeatNumber]) values(?,?,?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[TripReserve] set ReserveNumber = ? , PassengerCount = ? , UserID = ? , TripID = ? , Price = ? , SeatNumber = ? where ReserveID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public TripReserve findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                TripReserve tripReserve = new TripReserve();
                tripReserve.setReserveNumber(result.getString("ReserveNumber"));
                tripReserve.setPassengerCont(result.getInt("PassengerCount"));
                tripReserve.setUserId(result.getInt("UserID"));
                tripReserve.setTripId(result.getInt("TripID"));
                tripReserve.setPrice(result.getDouble("Price"));
                tripReserve.setSeatNumber(result.getInt("SeatNumber"));
                tripReserve.setId(result.getLong(1));
                return tripReserve;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TripReserve> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<TripReserve> tripReserves = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    TripReserve tripReserve = new TripReserve();
                    tripReserve.setReserveNumber(result.getString("ReserveNumber"));
                    tripReserve.setPassengerCont(result.getInt("PassengerCount"));
                    tripReserve.setUserId(result.getInt("UserID"));
                    tripReserve.setTripId(result.getInt("TripID"));
                    tripReserve.setPrice(result.getDouble("Price"));
                    tripReserve.setSeatNumber(result.getInt("SeatNumber"));
                    tripReserve.setId(result.getLong(1));
                    tripReserves.add(tripReserve);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tripReserves;
    }

    @Override
    public List<TripReserve> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<TripReserve> tripReserves = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                TripReserve tripReserve = new TripReserve();
                tripReserve.setReserveNumber(result.getString("ReserveNumber"));
                tripReserve.setPassengerCont(result.getInt("PassengerCount"));
                tripReserve.setUserId(result.getInt("UserID"));
                tripReserve.setTripId(result.getInt("TripID"));
                tripReserve.setPrice(result.getDouble("Price"));
                tripReserve.setSeatNumber(result.getInt("SeatNumber"));
                tripReserve.setId(result.getLong(1));
                tripReserves.add(tripReserve);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tripReserves;
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
    public TripReserve save(TripReserve E) {
        TripReserve tripReserve  = findById(E.getId());
        if(tripReserve != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setString(1,E.getReserveNumber());
                statement.setInt(2,E.getPassengerCont());
                statement.setLong(3,E.getUserId());
                statement.setLong(4, E.getTripId());
                statement.setDouble(5, E.getPrice());
                statement.setInt(6, E.getSeatNumber());
                statement.setLong(7, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, E.getId());
                statement.setString(2,E.getReserveNumber());
                statement.setInt(3,E.getPassengerCont());
                statement.setLong(4,E.getUserId());
                statement.setLong(5, E.getTripId());
                statement.setDouble(6, E.getPrice());
                statement.setInt(7, E.getSeatNumber());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
