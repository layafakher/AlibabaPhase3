package Repository.DAO;

import Model.Airport;
import Model.Hotel;
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

public class HotelDAO implements Repository<Hotel, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    HotelDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Hotel]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Hotel] where HotelID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Hotel] where HotelID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Hotel]([HotelID],[Name],[City],[Location],[MinPrice],[MaxPrice],[Rating]," +
                            "[Popularity],[ResidenceType],[IsVip]) values(?,?,?,?,?,?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Hotel] set [Name] = ? , City = ? , Location = ? , MinPrice = ? ," +
                            " MaxPrice = ? , Rating = ? , Popularity = ? , ResidenceType = ? , IsVip = ? where HotelID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Hotel findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Hotel hotel = new Hotel();
                hotel.setName(result.getString("Name"));
                hotel.setCity(result.getString("City"));
                hotel.setLocation(result.getString("Location"));
                hotel.setLocation(result.getString("Location"));
                hotel.setMinPrice(result.getDouble("MinPrice"));
                hotel.setMaxPrice(result.getDouble("MaxPrice"));
                hotel.setRating(result.getDouble("Rating"));
                hotel.setPopularity(result.getDouble("Popularity"));
                hotel.setResidenceType(result.getString("ResidenceType"));
                hotel.setId(result.getLong(1));
                return hotel;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hotel> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Hotel> hotels = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    Hotel hotel = new Hotel();
                    hotel.setName(result.getString("Name"));
                    hotel.setCity(result.getString("City"));
                    hotel.setLocation(result.getString("Location"));
                    hotel.setLocation(result.getString("Location"));
                    hotel.setMinPrice(result.getDouble("MinPrice"));
                    hotel.setMaxPrice(result.getDouble("MaxPrice"));
                    hotel.setRating(result.getDouble("Rating"));
                    hotel.setPopularity(result.getDouble("Popularity"));
                    hotel.setResidenceType(result.getString("ResidenceType"));
                    hotel.setId(result.getLong(1));
                    hotels.add(hotel);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotels;
    }

    @Override
    public List<Hotel> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Hotel> hotels = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                Hotel hotel = new Hotel();
                hotel.setName(result.getString("Name"));
                hotel.setCity(result.getString("City"));
                hotel.setLocation(result.getString("Location"));
                hotel.setLocation(result.getString("Location"));
                hotel.setMinPrice(result.getDouble("MinPrice"));
                hotel.setMaxPrice(result.getDouble("MaxPrice"));
                hotel.setRating(result.getDouble("Rating"));
                hotel.setPopularity(result.getDouble("Popularity"));
                hotel.setResidenceType(result.getString("ResidenceType"));
                hotel.setId(result.getLong(1));
                hotels.add(hotel);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotels;
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
    public Hotel save(Hotel E) {
        Hotel hotel  = findById(E.getId());
        if(hotel != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getName());
                statement.setNString(2,E.getCity());
                statement.setNString(3,E.getLocation());
                statement.setDouble(4,E.getMinPrice());
                statement.setDouble(5,E.getMaxPrice());
                statement.setDouble(6,E.getRating());
                statement.setDouble(7,E.getPopularity());
                statement.setNString(8,E.getResidenceType());
                statement.setBoolean(9,E.isVip());
                statement.setLong(10, E.getId());
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
                statement.setNString(4,E.getLocation());
                statement.setDouble(5,E.getMinPrice());
                statement.setDouble(6,E.getMaxPrice());
                statement.setDouble(7,E.getRating());
                statement.setDouble(8,E.getPopularity());
                statement.setNString(9,E.getResidenceType());
                statement.setBoolean(10,E.isVip());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
