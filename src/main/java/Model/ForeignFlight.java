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

    public ForeignFlight(String ticketType, boolean isOneWay, Date arrivalTime, Long returnFlightId) {
        super(ticketType, isOneWay, arrivalTime, returnFlightId);
    }

    @Override
    public String getTicketType() {
        return super.getTicketType();
    }

    @Override
    public void setTicketType(String ticketType) {
        super.setTicketType(ticketType);
    }

    @Override
    public boolean isOneWay() {
        return super.isOneWay();
    }

    @Override
    public void setOneWay(boolean oneWay) {
        super.setOneWay(oneWay);
    }

    @Override
    public Date getArrivalTime() {
        return super.getArrivalTime();
    }

    @Override
    public void setArrivalTime(Date arrivalTime) {
        super.setArrivalTime(arrivalTime);
    }

    @Override
    public Long getReturnFlightId() {
        return super.getReturnFlightId();
    }

    @Override
    public void setReturnFlightId(Long returnFlightId) {
        super.setReturnFlightId(returnFlightId);
    }

    public ForeignFlight(String origin, String destination, Date departureTime, int capacity) {
        super(origin, destination, departureTime, capacity);
    }

    @Override
    public String getOrigin() {
        return super.getOrigin();
    }

    @Override
    public void setOrigin(String origin) {
        super.setOrigin(origin);
    }

    @Override
    public String getDestination() {
        return super.getDestination();
    }

    @Override
    public void setDestination(String destination) {
        super.setDestination(destination);
    }

    @Override
    public Date getDepartureTime() {
        return super.getDepartureTime();
    }

    @Override
    public void setDepartureTime(Date departureTime) {
        super.setDepartureTime(departureTime);
    }

    @Override
    public int getCapacity() {
        return super.getCapacity();
    }

    @Override
    public void setCapacity(int capacity) {
        super.setCapacity(capacity);
    }
}
