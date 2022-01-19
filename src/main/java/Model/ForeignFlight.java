package Model;

import java.util.Date;

public class ForeignFlight extends Flight<ForeignFlight> {

    private double flightDuration;
    private int numberOfStops;

    public ForeignFlight() {
    }

    public ForeignFlight(double flightDuration, int numberOfStops) {
        this.flightDuration = flightDuration;
        this.numberOfStops = numberOfStops;
    }

    public ForeignFlight(String ticketType, boolean isOneWay, Date arrivalTime, Long returnFlightId, double flightDuration, int numberOfStops) {
        super(ticketType, isOneWay, arrivalTime, returnFlightId);
        this.flightDuration = flightDuration;
        this.numberOfStops = numberOfStops;
    }

    public double getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(double flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }
}
