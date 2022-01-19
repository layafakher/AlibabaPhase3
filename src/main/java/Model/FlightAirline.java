package Model;

public class FlightAirline extends BaseEntity<Long>  {

    private int flightId;
    private int airlineId;

    public FlightAirline() {
    }

    public FlightAirline(int flightId, int airlineId) {
        this.flightId = flightId;
        this.airlineId = airlineId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }
}
