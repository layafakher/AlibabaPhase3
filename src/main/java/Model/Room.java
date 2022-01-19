package Model;

public class Room extends BaseEntity<Long>{

    private boolean isVip;
    private int numberOfBeds;
    private boolean bAndB;
    private int hotelId;
    private int hotelReserveId;

    public Room() {
    }

    public Room(boolean isVip, int numberOfBeds, boolean bAndB, int hotelId, int hotelReserveId) {
        this.isVip = isVip;
        this.numberOfBeds = numberOfBeds;
        this.bAndB = bAndB;
        this.hotelId = hotelId;
        this.hotelReserveId = hotelReserveId;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean isbAndB() {
        return bAndB;
    }

    public void setbAndB(boolean bAndB) {
        this.bAndB = bAndB;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getHotelReserveId() {
        return hotelReserveId;
    }

    public void setHotelReserveId(int hotelReserveId) {
        this.hotelReserveId = hotelReserveId;
    }
}
