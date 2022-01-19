package Repository.DAO;

import Model.AirlineCompany;
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

public class AirlineCompanyDAO implements Repository<AirlineCompany, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    public AirlineCompanyDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[AirlineCompany]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[AirlineCompany] where AirlineID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[AirlineCompany] where AirlineID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[AirlineCompany]([AirlineID],[AirlineName]) values(?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[AirlineCompany] set AirlineName = ? where AirlineID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public AirlineCompany findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                AirlineCompany airlineCompany = new AirlineCompany();
                airlineCompany.setAirlineName(result.getString("AirlineName"));
                airlineCompany.setId(result.getLong(1));
                return airlineCompany;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AirlineCompany> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<AirlineCompany> airlineCompanies = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    AirlineCompany airlineCompany = new AirlineCompany();
                    airlineCompany.setAirlineName(result.getString("AirlineName"));
                    airlineCompany.setId(result.getLong(1));
                    airlineCompanies.add(airlineCompany);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airlineCompanies;
    }

    @Override
    public List<AirlineCompany> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<AirlineCompany> airlineCompanies = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                AirlineCompany airlineCompany = new AirlineCompany();
                airlineCompany.setAirlineName(result.getString("AirlineName"));
                airlineCompany.setId(result.getLong(1));
                airlineCompanies.add(airlineCompany);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airlineCompanies;
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
    public AirlineCompany save(AirlineCompany E) {
        AirlineCompany airlineCompany  = findById(E.getId());
        if(airlineCompany != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getAirlineName());
                statement.setLong(2, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, E.getId());
                statement.setNString(2,E.getAirlineName());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
