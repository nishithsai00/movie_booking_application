package com.nishith.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PaymentSimulation {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    int id;

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="BookingId")
    private Booking bookingid;
    private int ammount_in_rs;
    private String status;
    private LocalDateTime bookingtime;

    public LocalDateTime getBookingtime() {
        return bookingtime;
    }

    public void setBookingtime(LocalDateTime bookingtime) {
        this.bookingtime = bookingtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }


    public Booking getBookingid() {
        return bookingid;
    }

    public void setBookingid(Booking bookingid) {
        this.bookingid = bookingid;
    }

    public int getAmmount_in_rs() {
        return ammount_in_rs;
    }

    public void setAmmount_in_rs(int ammount_in_rs) {
        this.ammount_in_rs = ammount_in_rs;
    }
}
