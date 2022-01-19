package Repository.DAO;

import Model.Airport;
import Model.User;
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

public class UserDAO implements Repository<User, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    UserDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[User]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[User] where UserID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[User] where UserID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[User]([UserID],[Name],[Password],[PhoneNumber],[Email],[Credit],[NationalCode," +
                            "[Gender],[DateOfBirth],[Telephone],[AccountNumber],[LastName],[SignUpDate]]) values(?,?,?,?,?,?,?,?,?,?,?,?,?)"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[User] set [Name] = ? , Password = ? , PhoneNumber = ? , Email = ?" +
                            ", Credit = ? , NationalCode = ? , Gender = ? , DateOfBirth = ? , Telephone = ? , AccountNumber = ?" +
                            ", LastName = ? , SignUpDate = ? where UserID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public User findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                User user = new User();
                user.setName(result.getString("Name"));
                user.setPassword(result.getString("Password"));
                user.setPassword(result.getString("PhoneNumber"));
                user.setEmail(result.getString("Email"));
                user.setCredit(result.getDouble("Credit"));
                user.setNationalCode(result.getString("NationalCode"));
                user.setGender(result.getBoolean("Gender"));
                user.setDateOfBirth(result.getDate("DateOfBirth"));
                user.setTelephone(result.getString("Telephone"));
                user.setAccountNumber(result.getString("AccountNumber"));
                user.setLastName(result.getString("LastName"));
                user.setSignUpDate(result.getDate("SignUpDate"));
                user.setPhoneNumber(result.getString("PhoneNumber"));
                user.setId(result.getLong(1));
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<User> users = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    User user = new User();
                    user.setName(result.getString("Name"));
                    user.setPassword(result.getString("Password"));
                    user.setPassword(result.getString("PhoneNumber"));
                    user.setEmail(result.getString("Email"));
                    user.setCredit(result.getDouble("Credit"));
                    user.setNationalCode(result.getString("NationalCode"));
                    user.setGender(result.getBoolean("Gender"));
                    user.setDateOfBirth(result.getDate("DateOfBirth"));
                    user.setTelephone(result.getString("Telephone"));
                    user.setAccountNumber(result.getString("AccountNumber"));
                    user.setLastName(result.getString("LastName"));
                    user.setSignUpDate(result.getDate("SignUpDate"));
                    user.setPhoneNumber(result.getString("PhoneNumber"));
                    user.setId(result.getLong(1));
                    users.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<User> users = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setName(result.getString("Name"));
                user.setPassword(result.getString("Password"));
                user.setPassword(result.getString("PhoneNumber"));
                user.setEmail(result.getString("Email"));
                user.setCredit(result.getDouble("Credit"));
                user.setNationalCode(result.getString("NationalCode"));
                user.setGender(result.getBoolean("Gender"));
                user.setDateOfBirth(result.getDate("DateOfBirth"));
                user.setTelephone(result.getString("Telephone"));
                user.setAccountNumber(result.getString("AccountNumber"));
                user.setLastName(result.getString("LastName"));
                user.setSignUpDate(result.getDate("SignUpDate"));
                user.setPhoneNumber(result.getString("PhoneNumber"));
                user.setId(result.getLong(1));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
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
    public User save(User E) {
        User user  = findById(E.getId());
        if(user != null){
            PreparedStatement statement = statements.get(UPDATE);
            try {
                statement.setNString(1,E.getName());
                statement.setNString(2,E.getPassword());
                statement.setNString(3,E.getPhoneNumber());
                statement.setNString(4,E.getEmail());
                statement.setDouble(5,E.getCredit());
                statement.setNString(6,E.getNationalCode());
                statement.setBoolean(7,E.isGender());
                statement.setDate(8,new java.sql.Date(E.getDateOfBirth().getTime()));
                statement.setNString(9,E.getTelephone());
                statement.setNString(10,E.getAccountNumber());
                statement.setNString(11,E.getLastName());
                statement.setDate(12,new java.sql.Date(E.getSignUpDate().getTime()));
                statement.setLong(13, E.getId());
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
                statement.setNString(3,E.getPassword());
                statement.setNString(4,E.getPhoneNumber());
                statement.setNString(5,E.getEmail());
                statement.setDouble(6,E.getCredit());
                statement.setNString(7,E.getNationalCode());
                statement.setBoolean(8,E.isGender());
                statement.setDate(9,new java.sql.Date(E.getDateOfBirth().getTime()));
                statement.setNString(10,E.getTelephone());
                statement.setNString(11,E.getAccountNumber());
                statement.setNString(12,E.getLastName());
                statement.setDate(13,new java.sql.Date(E.getSignUpDate().getTime()));
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
