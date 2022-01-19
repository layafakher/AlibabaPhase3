package Repository.DAO;

import Model.Facility;
import Model.HotelFacility;
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

public class HotelFacilityDAO implements Repository<HotelFacility, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    HotelFacilityDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[HotelFacility]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[HotelFacility] where HotelFacilityID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[HotelFacility] where HotelFacilityID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[HotelFacility]([HotelFacilityID],[HotelID],[FacilityID]) values(?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[HotelFacility] set HotelID = ? , FacilityID = ? where HotelFacilityID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public HotelFacility findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                HotelFacility hotelFacility = new HotelFacility();
                hotelFacility.setHotelId(result.getInt("HotelID"));
                hotelFacility.setFacilityId(result.getInt("FacilityID"));
                hotelFacility.setId(result.getLong(1));
                return hotelFacility;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HotelFacility> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<HotelFacility> hotelFacilities = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    HotelFacility hotelFacility = new HotelFacility();
                    hotelFacility.setHotelId(result.getInt("HotelID"));
                    hotelFacility.setFacilityId(result.getInt("FacilityID"));
                    hotelFacility.setId(result.getLong(1));
                    hotelFacilities.add(hotelFacility);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelFacilities;
    }

    @Override
    public List<HotelFacility> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<HotelFacility> hotelFacilities = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                HotelFacility hotelFacility = new HotelFacility();
                hotelFacility.setHotelId(result.getInt("HotelID"));
                hotelFacility.setFacilityId(result.getInt("FacilityID"));
                hotelFacility.setId(result.getLong(1));
                hotelFacilities.add(hotelFacility);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelFacilities;
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
    public HotelFacility save(HotelFacility E) {
        HotelFacility hotelFacility  = findById(E.getId());
        if(hotelFacility != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setLong(1, E.getHotelId());
                statement.setLong(2, E.getFacilityId());
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
                statement.setLong(2, E.getHotelId());
                statement.setLong(3, E.getFacilityId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
