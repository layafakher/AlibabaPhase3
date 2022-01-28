package Model;

import java.util.Date;

public class Flight<T extends Flight<T>> extends Trip<T> {

    private String ticketType;
    private boolean isOneWay;
    private Date arrivalTime;
    private Long returnFlightId;

    public Flight() {
    }

    public Flight(String ticketType, boolean isOneWay, Date arrivalTime, Long returnFlightId) {
        this.ticketType = ticketType;
        this.isOneWay = isOneWay;
        this.arrivalTime = arrivalTime;
        this.returnFlightId = returnFlightId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public void setOneWay(boolean oneWay) {
        isOneWay = oneWay;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Long getReturnFlightId() {
        return returnFlightId;
    }

    public void setReturnFlightId(Long returnFlightId) {
        this.returnFlightId = returnFlightId;
    }

    public Flight(String origin, String destination, Date departureTime, int capacity) {
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
