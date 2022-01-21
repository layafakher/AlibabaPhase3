package com.dbms.alibabaphase3;

import javafx.beans.property.SimpleStringProperty;

public class TransactionView {

    private SimpleStringProperty transTime;
    private SimpleStringProperty amount;
    private SimpleStringProperty type;

    public TransactionView(String transTime, String amount, String type) {
        this.transTime = new SimpleStringProperty(transTime);
        this.amount = new SimpleStringProperty(amount);
        this.type = new SimpleStringProperty(type);
    }

    public String getTransTime() {
        return transTime.get();
    }

    public SimpleStringProperty transTimeProperty() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime.set(transTime);
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
