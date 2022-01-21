package Repository.DAO;

import Model.Airport;
import Model.Transaction;
import Repository.Repository;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static Repository.DAO.StatementType.*;

public class TransactionDAO implements Repository<Transaction, Long> {

    private final EnumMap<StatementType, PreparedStatement> statements;

    TransactionDAO(Connection connection) {
        statements = new EnumMap<>(StatementType.class);
        prepareStatements(connection);
    }

    private void prepareStatements(Connection connection) {
        try {
            statements.put(FIND_ALL, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Transaction]"
            ));
            statements.put(FIND_BY_ID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Transaction] where TransID = ?"
            ));
            statements.put(FIND_BY_USERID, connection.prepareStatement(
                    "select * from [Alibaba].[dbo].[Transaction] where UserID = ?"
            ));
            statements.put(DELETE_BY_ID, connection.prepareStatement(
                    "delete from [Alibaba].[dbo].[Transaction] where TransID = ?"
            ));
            statements.put(INSERT, connection.prepareStatement(
                    "insert into [Alibaba].[dbo].[Transaction]([TransTime],[Amount],[Type],[UserID]) values(?,?,?,?)\n" +
                            "SELECT SCOPE_IDENTITY()"
            ));
            statements.put(UPDATE, connection.prepareStatement(
                    "update [Alibaba].[dbo].[Transaction] set TransTime = ? , Amount = ? , [Type] = ? , UserID = ? where TransID = ?"
            ));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Transaction findById(Long id) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        try {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransTime(result.getDate("TransTime"));
                transaction.setAmount(result.getDouble("Amount"));
                transaction.setType(result.getString("Type"));
                transaction.setUserId(result.getLong("UserID"));
                transaction.setId(result.getLong(1));
                return transaction;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Transaction> findByUserId(Long userId) {
        PreparedStatement statement = statements.get(FIND_BY_USERID);
        List<Transaction> transactions = new LinkedList<>();
        try {
            statement.setLong(1, userId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                transactions.add(findById(result.getLong(1)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findByIDs(Collection<Long> longs) {
        PreparedStatement statement = statements.get(FIND_BY_ID);
        ResultSet result;
        List<Transaction> transactions = new ArrayList<>();
        try {
            for (Long id : longs) {
                statement.setLong(1, id);
                result = statement.executeQuery();
                while (result.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransTime(result.getDate("TransTime"));
                    transaction.setAmount(result.getDouble("Amount"));
                    transaction.setType(result.getString("Type"));
                    transaction.setUserId(result.getLong("UserID"));
                    transaction.setId(result.getLong(1));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findAll() {
        PreparedStatement statement = statements.get(FIND_ALL);
        ResultSet result;
        List<Transaction> transactions = new ArrayList<>();
        try {
            result = statement.executeQuery();
            while (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransTime(result.getDate("TransTime"));
                transaction.setAmount(result.getDouble("Amount"));
                transaction.setType(result.getString("Type"));
                transaction.setUserId(result.getLong("UserID"));
                transaction.setId(result.getLong(1));
                transactions.add(transaction);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactions;
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
    public Transaction save(Transaction E) {
        if(E.getId() != null){
            Transaction transaction  = findById(E.getId());
            if(transaction != null){
                PreparedStatement statement = statements.get(UPDATE);
                try {
                    statement.setDate(1,new java.sql.Date(E.getTransTime().getTime()));
                    statement.setDouble(2,E.getAmount());
                    statement.setNString(3,E.getType());
                    statement.setLong(4, E.getUserId());
                    statement.setLong(5, E.getId());
                    statement.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        else{
            PreparedStatement statement = statements.get(INSERT);
            try {
                statement.setDate(1,new java.sql.Date(E.getTransTime().getTime()));
                statement.setDouble(2,E.getAmount());
                statement.setNString(3,E.getType());
                statement.setLong(4, E.getUserId());
                ResultSet result = statement.executeQuery();
                if(result.next()){
                    return findById(result.getLong(1));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return findById(E.getId());
    }
}
