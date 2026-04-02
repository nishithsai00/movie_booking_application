package com.nishith.demo.model;

import jakarta.persistence.*;

@Entity
public class SeatSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="masterid")
    private Shows showid;
    private String seat;
    private String status="Avilable";
    private int price=100;

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Shows getShowid() {
        return showid;
    }

    public void setShowid(Shows showid) {
        this.showid = showid;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
