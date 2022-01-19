package Model;

public class HotelFacility extends BaseEntity<Long>  {

    private int hotelId;
    private int facilityId;

    public HotelFacility() {
    }

    public HotelFacility(int hotelId, int facilityId) {
        this.hotelId = hotelId;
        this.facilityId = facilityId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }
}
