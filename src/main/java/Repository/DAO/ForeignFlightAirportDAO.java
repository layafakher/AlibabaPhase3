package Repository.DAO;

import Model.Facility;
import Model.ForeignFlightAirport;
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

public class ForeignFlightAirportDAO implements Repository<ForeignFlightAirport, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    ForeignFlightAirportDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[ForeignFlightAirport]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[ForeignFlightAirport] where ForeignFlightAirportID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[ForeignFlightAirport] where ForeignFlightAirportID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[ForeignFlightAirport]([ForeignFlightAirportID],[ForeignFlightID],[AirportID],[Duration]) values(?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[ForeignFlightAirport] set ForeignFlightID = ? , AirportID = ? , Duration = ? where ForeignFlightAirportID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ForeignFlightAirport findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                ForeignFlightAirport foreignFlightAirport = new ForeignFlightAirport();
                foreignFlightAirport.setForeignFlightId(result.getInt("ForeignFlightID"));
                foreignFlightAirport.setAirportId(result.getInt("AirportID"));
                foreignFlightAirport.setDuration(result.getDouble("Duration"));
                foreignFlightAirport.setId(result.getLong(1));
                return foreignFlightAirport;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ForeignFlightAirport> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<ForeignFlightAirport> foreignFlightAirports = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    ForeignFlightAirport foreignFlightAirport = new ForeignFlightAirport();
                    foreignFlightAirport.setForeignFlightId(result.getInt("ForeignFlightID"));
                    foreignFlightAirport.setAirportId(result.getInt("AirportID"));
                    foreignFlightAirport.setDuration(result.getDouble("Duration"));
                    foreignFlightAirport.setId(result.getLong(1));
                    foreignFlightAirports.add(foreignFlightAirport);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return foreignFlightAirports;
    }

    @Override
    public List<ForeignFlightAirport> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<ForeignFlightAirport> foreignFlightAirports = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                ForeignFlightAirport foreignFlightAirport = new ForeignFlightAirport();
                foreignFlightAirport.setForeignFlightId(result.getInt("ForeignFlightID"));
                foreignFlightAirport.setAirportId(result.getInt("AirportID"));
                foreignFlightAirport.setDuration(result.getDouble("Duration"));
                foreignFlightAirport.setId(result.getLong(1));
                foreignFlightAirports.add(foreignFlightAirport);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return foreignFlightAirports;
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
    public ForeignFlightAirport save(ForeignFlightAirport E) {
        ForeignFlightAirport foreignFlightAirport  = findById(E.getId());
        if(foreignFlightAirport != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setLong(1, E.getForeignFlightId());
                statement.setLong(2, E.getAirportId());
                statement.setDouble(3, E.getDuration());
                statement.setLong(4, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, E.getId());
                statement.setLong(2, E.getForeignFlightId());
                statement.setLong(3, E.getAirportId());
                statement.setDouble(4, E.getDuration());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
