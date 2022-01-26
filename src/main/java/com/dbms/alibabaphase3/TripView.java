package com.dbms.alibabaphase3;

public class TripView {
    private String origin;
    private String destination;
    private Long id;

    public TripView(String origin, String destination, Long id) {
        this.origin = origin;
        this.destination = destination;
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TripView{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", id=" + id +
                '}';
    }
}
