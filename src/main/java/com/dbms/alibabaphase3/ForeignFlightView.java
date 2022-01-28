package com.dbms.alibabaphase3;

import Model.HotelReserve;
import Model.TripReserve;
import Repository.DAO.HotelReserveDAO;
import Repository.DAO.RepositoryFacade;
import Repository.DAO.TripReserveDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class ForeignFlightView {
    private SimpleStringProperty exitDate;
    private SimpleStringProperty returnDate;
    private SimpleStringProperty amount;
    private SimpleStringProperty price;
    private SimpleStringProperty capacity;
    private SimpleStringProperty origin;
    private SimpleStringProperty destination;
    private SimpleStringProperty airline_company;
    private Long id;
    private double price2;
    private int cnt;
    private Button reserve;

    public ForeignFlightView() {
    }

    public ForeignFlightView(String exitDate, String returnDate,  String price, String capacity, String origin, String destination, String airline_company) {
        this.exitDate = new SimpleStringProperty(exitDate);
        this.returnDate = new SimpleStringProperty(returnDate);
        this.price = new SimpleStringProperty(price);
        this.capacity = new SimpleStringProperty(capacity);
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.airline_company = new SimpleStringProperty(airline_company);
        this.reserve = new Button("رزرو پرواز");
        reserve.setOnMouseClicked((mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to reserve this hotel?", ButtonType.YES,ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.YES){
                    TripReserve tripReserve = new TripReserve();
                    tripReserve.setTripId(id.intValue());
                    tripReserve.setPrice(Double.parseDouble(price));
                    tripReserve.setPassengerCont(Integer.parseInt(amount.getValue()));
                    tripReserve.setUserId(Info.getInstance().getUser().getId().intValue());

                    TripReserveDAO tripReserveDAO = (TripReserveDAO) RepositoryFacade.getInstance().getDao(TripReserve.class);
                    tripReserveDAO.save(tripReserve);
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Hotel reserved successfully", ButtonType.OK);
                    alert2.show();
                }
            });
        } ));
    }

    public String getExitDate() {
        return exitDate.get();
    }

    public SimpleStringProperty exitDateProperty() {
        return exitDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate.set(exitDate);
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public SimpleStringProperty returnDateProperty() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = new SimpleStringProperty(amount);;
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

    public String getCapacity() {
        return capacity.get();
    }

    public SimpleStringProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity.set(capacity);
    }

    public String getOrigin() {
        return origin.get();
    }

    public SimpleStringProperty originProperty() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin.set(origin);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getAirline_company() {
        return airline_company.get();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Button getReserve() {
        return reserve;
    }

    public void setReserve(Button reserve) {
        this.reserve = reserve;
    }
}
