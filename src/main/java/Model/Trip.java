package Model;

import java.util.Date;

public class Trip <T extends Trip<T>> extends BaseEntity<Long> {

    private String origin;
    private String destination;
    private Date departureTime;
    private int capacity;

    public Trip() {
    }

    public Trip(String origin, String destination, Date departureTime, int capacity) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.capacity = capacity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
