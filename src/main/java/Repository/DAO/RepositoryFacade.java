package Repository.DAO;

import Model.*;
import Repository.Repository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public final class RepositoryFacade {
    private static RepositoryFacade INSTANCE = null;

    private static String DB_URL;
    private static String USER_NAME;
    private static String PASSWORD;

    private Map<Class<?>, Repository<?, ? extends Serializable>> daos = new HashMap<>();

    private RepositoryFacade(Connection connection) {
        daos.put(AirlineCompany.class, new AirlineCompanyDAO(connection));
        daos.put(Airport.class, new AirportDAO(connection));
        daos.put(BusCompany.class, new BusCompanyDAO(connection));
        daos.put(BusTrip.class, new BusTripDAO(connection));
        daos.put(Facility.class, new FacilityDAO(connection));
        daos.put(FlightAirline.class, new FlightAirlineDAO(connection));
        daos.put(Flight.class, new FlightDAO(connection));
        daos.put(ForeignFlightAirport.class, new ForeignFlightAirportDAO(connection));
        daos.put(ForeignFlight.class, new ForeignFlightDAO(connection));
        daos.put(Hotel.class, new HotelDAO(connection));
        daos.put(HotelFacility.class, new HotelFacilityDAO(connection));
        daos.put(HotelReserve.class, new HotelReserveDAO(connection));
        daos.put(InlandFlight.class, new InlandFlightDAO(connection));
        daos.put(Room.class, new RoomDAO(connection));
        daos.put(Transaction.class, new TransactionDAO(connection));
        daos.put(Trip.class, new TripDAO(connection));
        daos.put(TripReserve.class, new TripReserveDAO(connection));
        daos.put(User.class, new UserDAO(connection));
    }

    public synchronized static RepositoryFacade getInstance() {
        if (INSTANCE == null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject configuration = (JSONObject) parser.parse(new FileReader("src/main/resources/com/dbms/alibabaphase3/configuration.json"));
                DB_URL = (String) configuration.get("DB_URL");
                USER_NAME = (String) configuration.get("USER_NAME");
                PASSWORD = (String) configuration.get("PASSWORD");
                INSTANCE = new RepositoryFacade(DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD));
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
        return INSTANCE;
    }

    public <ID extends Serializable, T extends BaseEntity<ID>> Repository<?, ? extends Serializable> getDao(Class<T> type){
        return daos.get(type);
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <ID extends Serializable, T extends BaseEntity<ID>> List<T> findAll(Class<T> type) {
        Repository<?, ?> repository = daos.get(type);
        if (repository != null) {
            return new ArrayList<>(((Repository<T, ID>) daos.get(type)).findAll());
        }
        return Collections.emptyList();
    }

    public <ID extends Serializable, T extends BaseEntity<ID>> Optional<T>
    findById(ID id, Class<T> type) {

        return Optional.ofNullable(daos.get(type))
                .map(repo -> (Repository<T, ID>) repo)
                .map(repo -> repo.findById(id));
    }

    public <ID extends Serializable, T extends BaseEntity<ID>>
    boolean deleteByID(ID id, Class<T> type) {
        return Optional.ofNullable(daos.get(type))
                .map(repo -> (Repository<T, ID>) repo)
                .map(repo -> repo.deleteByID(id))
                .orElse(Boolean.FALSE);
    }

    public <ID extends Serializable, T extends BaseEntity<ID>> Boolean
    deleteByIDs(Collection<ID> ids, Class<T> type) {
        return Optional.ofNullable(daos.get(type))
                .map(repo -> (Repository<T, ID>) repo)
                .map(repo -> repo.DeleteByIDs(ids))
                .orElse(Boolean.FALSE);
    }

    public <ID extends Serializable, T extends BaseEntity<ID>> List<T>
    findByIDs(Collection<ID> ids, Class<T> type) {
        return Optional.ofNullable(daos.get(type))
                .map(repo -> (Repository<T, ID>) repo)
                .map(repo -> new ArrayList<T>(repo.findByIDs(ids)))
                .orElse(new ArrayList<>());
    }

    public <ID extends Serializable, T extends BaseEntity<ID>> T save(T entity, Class<T> type) {
        Repository<?, ?> repository = daos.get(type);
        if (repository != null) {
            return (((Repository<T, ID>) daos.get(type)).save(entity));
        }
        else {
            return null;
        }
    }
}
