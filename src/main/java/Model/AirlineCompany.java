package Model;

public class AirlineCompany extends BaseEntity<Long>{

    private String airlineName;

    public AirlineCompany() {
    }

    public AirlineCompany(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
