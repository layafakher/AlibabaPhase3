package Model;

import java.util.Date;

public class Transaction extends BaseEntity<Long> {

    private Date transTime;
    private double amount;
    private String type;
    private Long userId;

    public Transaction() {
    }

    public Transaction(Date transTime, double amount, String type, Long userId) {
        this.transTime = transTime;
        this.amount = amount;
        this.type = type;
        this.userId = userId;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
