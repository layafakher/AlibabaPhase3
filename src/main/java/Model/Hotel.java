package Model;

public class Hotel extends BaseEntity<Long> {

    private String name;
    private String city;
    private String location;
    private double minPrice;
    private double maxPrice;
    private double rating;
    private double popularity;
    private String residenceType;
    private boolean isVip;

    public Hotel() {
    }

    public Hotel(String name, String city, String location, double minPrice, double maxPrice, double rating, double popularity, String residenceType, boolean isVip) {
        this.name = name;
        this.city = city;
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.rating = rating;
        this.popularity = popularity;
        this.residenceType = residenceType;
        this.isVip = isVip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }
}
