package com.dbms.alibabaphase3;

public class HotelView {
    private String city;
    private Long id;

    public HotelView(String city, Long id) {
        this.city = city;
        this.id = id;
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
}
