package com.dbms.alibabaphase3;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class HotelView {
    private String city;
    private Long id;
    private SimpleStringProperty name;
    private SimpleStringProperty residenceType;
    private SimpleStringProperty rating;
    private SimpleStringProperty location;
    private SimpleStringProperty hotelCity;
    private SimpleStringProperty price;
    private Button reserve;

    public HotelView(String city, Long id) {
        this.city = city;
        this.id = id;
    }

    public HotelView(String name, String residenceType, String rating, String location, String hotelCity, String price) {
        this.name = new SimpleStringProperty(name);
        this.residenceType = new SimpleStringProperty(residenceType);
        this.rating = new SimpleStringProperty(rating);
        this.location = new SimpleStringProperty(location);
        this.hotelCity = new SimpleStringProperty(hotelCity);
        this.price = new SimpleStringProperty(price);
        this.reserve = new Button("رزرو هتل");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return city;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getResidenceType() {
        return residenceType.get();
    }

    public SimpleStringProperty residenceTypeProperty() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType.set(residenceType);
    }

    public String getRating() {
        return rating.get();
    }

    public SimpleStringProperty ratingProperty() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating.set(rating);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getHotelCity() {
        return hotelCity.get();
    }

    public SimpleStringProperty hotelCityProperty() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity.set(hotelCity);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public Button getReserve() {
        return reserve;
    }

    public void setReserve(Button reserve) {
        this.reserve = reserve;
    }
}
