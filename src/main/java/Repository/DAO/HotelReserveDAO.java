package Repository.DAO;

import Model.Hotel;
import Model.HotelReserve;
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

public class HotelReserveDAO implements Repository<HotelReserve, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    HotelReserveDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[HotelReserve]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[HotelReserve] where ReserveID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[HotelReserve] where ReserveID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[HotelReserve]([ReserveID],[CheckInDate],[CheckOutDate],[Price],[PassengerCount],[NumberOfRooms]," +
                            "[UserID],[HotelID]) values(?,?,?,?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[HotelReserve] set CheckInDate = ? , CheckOutDate = ? , Price = ? , PassengerCount = ? ," +
                            " NumberOfRooms = ? , UserID = ? , HotelID = ? where ReserveID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public HotelReserve findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                HotelReserve hotelReserve = new HotelReserve();
                hotelReserve.setCheckinDate(result.getDate("CheckInDate"));
                hotelReserve.setCheckoutDate(result.getDate("CheckOutDate"));
                hotelReserve.setPrice(result.getDouble("Price"));
                hotelReserve.setPassengerCount(result.getInt("PassengerCount"));
                hotelReserve.setNumberOfRooms(result.getInt("NumberOfRooms"));
                hotelReserve.setUserId(result.getInt("UserID"));
                hotelReserve.setHotelId(result.getInt("HotelID"));
                hotelReserve.setId(result.getLong(1));
                return hotelReserve;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HotelReserve> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<HotelReserve> hotelReserves = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    HotelReserve hotelReserve = new HotelReserve();
                    hotelReserve.setCheckinDate(result.getDate("CheckInDate"));
                    hotelReserve.setCheckoutDate(result.getDate("CheckOutDate"));
                    hotelReserve.setPrice(result.getDouble("Price"));
                    hotelReserve.setPassengerCount(result.getInt("PassengerCount"));
                    hotelReserve.setNumberOfRooms(result.getInt("NumberOfRooms"));
                    hotelReserve.setUserId(result.getInt("UserID"));
                    hotelReserve.setHotelId(result.getInt("HotelID"));
                    hotelReserve.setId(result.getLong(1));
                    hotelReserves.add(hotelReserve);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelReserves;
    }

    @Override
    public List<HotelReserve> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<HotelReserve> hotelReserves = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                HotelReserve hotelReserve = new HotelReserve();
                hotelReserve.setCheckinDate(result.getDate("CheckInDate"));
                hotelReserve.setCheckoutDate(result.getDate("CheckOutDate"));
                hotelReserve.setPrice(result.getDouble("Price"));
                hotelReserve.setPassengerCount(result.getInt("PassengerCount"));
                hotelReserve.setNumberOfRooms(result.getInt("NumberOfRooms"));
                hotelReserve.setUserId(result.getInt("UserID"));
                hotelReserve.setHotelId(result.getInt("HotelID"));
                hotelReserve.setId(result.getLong(1));
                hotelReserves.add(hotelReserve);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelReserves;
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
    public HotelReserve save(HotelReserve E) {
        HotelReserve hotelReserve  = findById(E.getId());
        if(hotelReserve != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setDate(1,new java.sql.Date(E.getCheckinDate().getTime()));
                statement.setDate(2,new java.sql.Date(E.getCheckoutDate().getTime()));
                statement.setDouble(3,E.getPrice());
                statement.setInt(4,E.getPassengerCount());
                statement.setInt(5,E.getNumberOfRooms());
                statement.setLong(6,E.getUserId());
                statement.setLong(7,E.getHotelId());
                statement.setLong(8, E.getId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setLong(1, E.getId());
                statement.setDate(2,new java.sql.Date(E.getCheckinDate().getTime()));
                statement.setDate(3,new java.sql.Date(E.getCheckoutDate().getTime()));
                statement.setDouble(4,E.getPrice());
                statement.setInt(5,E.getPassengerCount());
                statement.setInt(6,E.getNumberOfRooms());
                statement.setLong(7,E.getUserId());
                statement.setLong(8,E.getHotelId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
