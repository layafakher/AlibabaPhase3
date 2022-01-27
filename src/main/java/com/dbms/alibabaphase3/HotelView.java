package com.dbms.alibabaphase3;

import Model.HotelReserve;
import Repository.DAO.HotelReserveDAO;
import Repository.DAO.RepositoryFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class HotelView {
    private String city;
    private Long id;
    private double priceDouble;
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
        reserve.setOnMouseClicked((mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to reserve this hotel?", ButtonType.YES,ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.YES){
                    HotelReserve hotelReserve = new HotelReserve();
                    hotelReserve.setUserId(Info.getInstance().getUser().getId().intValue());
                    hotelReserve.setNumberOfRooms(HotelReserveController.roomCount);
                    hotelReserve.setPassengerCount(HotelReserveController.passengerCount);
                    hotelReserve.setCheckinDate(java.sql.Date.valueOf(HotelReserveController.entryDate));
                    hotelReserve.setCheckoutDate(java.sql.Date.valueOf(HotelReserveController.exitDate));
                    hotelReserve.setPrice(priceDouble);
                    hotelReserve.setHotelId(id.intValue());

                    HotelReserveDAO hotelReserveDAO = (HotelReserveDAO) RepositoryFacade.getInstance().getDao(HotelReserve.class);
                    hotelReserveDAO.save(hotelReserve);
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Hotel reserved Successfully", ButtonType.OK);
                    alert2.show();
                }
            });
        } ));
    }

    public double getPriceDouble() {
        return priceDouble;
    }

    public void setPriceDouble(double priceDouble) {
        this.priceDouble = priceDouble;
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
