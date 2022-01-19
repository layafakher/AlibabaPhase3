package Repository.DAO;

import Model.Airport;
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

public class AirportDAO implements Repository<Airport, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    public AirportDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Airport]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Airport] where AirportID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Airport] where AirportID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Airport]([AirportID],[Name],[City],[Country]) values(?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Airport] set [Name] = ? , City = ? , Country = ? where AirportID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Airport findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Airport airport = new Airport();
                airport.setCity(result.getString("City"));
                airport.setCountry(result.getString("Country"));
                airport.setName(result.getString("Name"));
                airport.setId(result.getLong(1));
                return airport;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Airport> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Airport> airports = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    Airport airport = new Airport();
                    airport.setCity(result.getString("City"));
                    airport.setCountry(result.getString("Country"));
                    airport.setName(result.getString("Name"));
                    airport.setId(result.getLong(1));
                    airports.add(airport);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airports;
    }

    @Override
    public List<Airport> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Airport> airports = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                Airport airport = new Airport();
                airport.setCity(result.getString("City"));
                airport.setCountry(result.getString("Country"));
                airport.setName(result.getString("Name"));
                airport.setId(result.getLong(1));
                airports.add(airport);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airports;
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
    public Airport save(Airport E) {
        Airport airport  = findById(E.getId());
        if(airport != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getName());
                statement.setNString(2,E.getCity());
                statement.setNString(3,E.getCountry());
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
                statement.setNString(2,E.getName());
                statement.setNString(3,E.getCity());
                statement.setNString(4,E.getCountry());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
