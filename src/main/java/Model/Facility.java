package Model;

public class Facility extends BaseEntity<Long> {

    private String facilityName;

    public Facility() {
    }

    public Facility(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}
