package Repository.DAO;

import Model.Facility;
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

public class FacilityDAO implements Repository<Facility, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    FacilityDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Facility]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Facility] where FacilityID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Facility] where FacilityID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Facility]([FacilityID],[FacilityName]) values(?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Facility] set FacilityName = ? where FacilityID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Facility findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Facility facility = new Facility();
                facility.setFacilityName(result.getString("FacilityName"));
                facility.setId(result.getLong(1));
                return facility;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Facility> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Facility> facilities = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    Facility facility = new Facility();
                    facility.setFacilityName(result.getString("FacilityName"));
                    facility.setId(result.getLong(1));
                    facilities.add(facility);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return facilities;
    }

    @Override
    public List<Facility> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Facility> facilities = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                Facility facility = new Facility();
                facility.setFacilityName(result.getString("FacilityName"));
                facility.setId(result.getLong(1));
                facilities.add(facility);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return facilities;
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
    public Facility save(Facility E) {
        Facility facility  = findById(E.getId());
        if(facility != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setString(1, E.getFacilityName());
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
                statement.setString(2, E.getFacilityName());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
