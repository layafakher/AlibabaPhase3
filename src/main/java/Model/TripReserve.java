package Model;

public class TripReserve extends BaseEntity<Long>{

    private String reserveNumber;
    private int passengerCont;
    private int userId;
    private int tripId;
    private double price;
    private int seatNumber;

    public TripReserve() {
    }

    public TripReserve(String reserveNumber, int passengerCont, int userId, int tripId, double price, int seatNumber) {
        this.reserveNumber = reserveNumber;
        this.passengerCont = passengerCont;
        this.userId = userId;
        this.tripId = tripId;
        this.price = price;
        this.seatNumber = seatNumber;
    }

    public String getReserveNumber() {
        return reserveNumber;
    }

    public void setReserveNumber(String reserveNumber) {
        this.reserveNumber = reserveNumber;
    }

    public int getPassengerCont() {
        return passengerCont;
    }

    public void setPassengerCont(int passengerCont) {
        this.passengerCont = passengerCont;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
