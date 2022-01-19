package Model;

import java.util.Date;

public class HotelReserve extends BaseEntity<Long> {

    private Date checkinDate;
    private Date checkoutDate;
    private double price;
    private int passengerCount;
    private int numberOfRooms;
    private int userId;
    private int hotelId;

    public HotelReserve() {
    }

    public HotelReserve(Date checkinDate, Date checkoutDate, double price, int passengerCount, int numberOfRooms, int userId, int hotelId) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
        this.passengerCount = passengerCount;
        this.numberOfRooms = numberOfRooms;
        this.userId = userId;
        this.hotelId = hotelId;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
