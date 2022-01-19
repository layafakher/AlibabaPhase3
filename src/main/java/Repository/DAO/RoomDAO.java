package Repository.DAO;

import Model.Room;
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

public class RoomDAO implements Repository<Room, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    RoomDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Room]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Room] where RoomID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Room] where RoomID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Room]([RoomID],[IsVip],[NumberOfBeds],[BAndB],[HotelID],[HotelReserveID]) values(?,?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Room] set IsVip = ? , NumberOfBeds = ? , BAndB = ?,.HotelID = ? , HotelReserveID = ? where RoomID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Room findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Room room = new Room();
                room.setVip(result.getBoolean("IsVip"));
                room.setNumberOfBeds(result.getInt("NumberOfBeds"));
                room.setbAndB(result.getBoolean("BAndB"));
                room.setHotelId(result.getInt("HotelID"));
                room.setHotelReserveId(result.getInt("HotelReserveID"));
                room.setId(result.getLong(1));
                return room;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Room> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Room> rooms = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    Room room = new Room();
                    room.setVip(result.getBoolean("IsVip"));
                    room.setNumberOfBeds(result.getInt("NumberOfBeds"));
                    room.setbAndB(result.getBoolean("BAndB"));
                    room.setHotelId(result.getInt("HotelID"));
                    room.setHotelReserveId(result.getInt("HotelReserveID"));
                    room.setId(result.getLong(1));
                    rooms.add(room);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<Room> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Room> rooms = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                Room room = new Room();
                room.setVip(result.getBoolean("IsVip"));
                room.setNumberOfBeds(result.getInt("NumberOfBeds"));
                room.setbAndB(result.getBoolean("BAndB"));
                room.setHotelId(result.getInt("HotelID"));
                room.setHotelReserveId(result.getInt("HotelReserveID"));
                room.setId(result.getLong(1));
                rooms.add(room);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rooms;
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
    public Room save(Room E) {
        Room room  = findById(E.getId());
        if(room != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setBoolean(1,E.isVip());
                statement.setInt(2,E.getNumberOfBeds());
                statement.setBoolean(3,E.isbAndB());
                statement.setInt(4,E.getHotelId());
                statement.setInt(5,E.getHotelReserveId());
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
                statement.setBoolean(2,E.isVip());
                statement.setInt(3,E.getNumberOfBeds());
                statement.setBoolean(4,E.isbAndB());
                statement.setInt(5,E.getHotelId());
                statement.setInt(6,E.getHotelReserveId());
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
