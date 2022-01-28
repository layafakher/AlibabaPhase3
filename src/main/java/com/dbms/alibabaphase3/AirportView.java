package com.dbms.alibabaphase3;

import javafx.beans.property.SimpleStringProperty;

public class AirportView {

    private String name;
    private String city;
    private String country;
    private Long id;

    public AirportView(String name, String city, String country, Long id) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
