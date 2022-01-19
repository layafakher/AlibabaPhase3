package Repository.DAO;

import Model.BusCompany;
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

public class BusCompanyDAO implements Repository<BusCompany, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    BusCompanyDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[BusCompany]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[BusCompany] where BusCompanyID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[BusCompany] where BusCompanyID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[BusCompany]([BusCompanyID],[Name]) values(?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[BusCompany] set [Name] = ? where BusCompanyID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BusCompany findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                BusCompany busCompany = new BusCompany();
                busCompany.setName(result.getString("Name"));
                busCompany.setId(result.getLong(1));
                return busCompany;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BusCompany> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<BusCompany> busCompanies = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    BusCompany busCompany = new BusCompany();
                    busCompany.setName(result.getString("Name"));
                    busCompany.setId(result.getLong(1));
                    busCompanies.add(busCompany);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return busCompanies;
    }

    @Override
    public List<BusCompany> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<BusCompany> busCompanies = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                BusCompany busCompany = new BusCompany();
                busCompany.setName(result.getString("Name"));
                busCompany.setId(result.getLong(1));
                busCompanies.add(busCompany);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return busCompanies;
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
    public BusCompany save(BusCompany E) {
        BusCompany busCompany  = findById(E.getId());
        if(busCompany != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getName());
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
                statement.setNString(2,E.getName());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
