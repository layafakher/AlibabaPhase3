package com.dbms.alibabaphase3;

import javafx.beans.property.SimpleStringProperty;

public class AirportView {

    private String name;
    private Long id;

    public AirportView(String name, Long id) {
        this.name = name;
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
}
