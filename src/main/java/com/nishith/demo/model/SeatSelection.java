package com.nishith.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SeatSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="masterid")
    private Shows showid;
    private String seats;
    private String status;


    public Shows getShowid() {
        return showid;
    }

    public void setShowid(Shows showid) {
        this.showid = showid;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;  
    }
}
