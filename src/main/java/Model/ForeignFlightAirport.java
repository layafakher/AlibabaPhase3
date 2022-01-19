package Model;

public class ForeignFlightAirport extends BaseEntity<Long>  {

    private int foreignFlightId;
    private int airportId;
    private double duration;

    public ForeignFlightAirport() {
    }

    public ForeignFlightAirport(int foreignFlightId, int airportId, double duration) {
        this.foreignFlightId = foreignFlightId;
        this.airportId = airportId;
        this.duration = duration;
    }

    public int getForeignFlightId() {
        return foreignFlightId;
    }

    public void setForeignFlightId(int foreignFlightId) {
        this.foreignFlightId = foreignFlightId;
    }

    public int getAirportId() {
        return airportId;
    }

    public void setAirportId(int airportId) {
        this.airportId = airportId;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
