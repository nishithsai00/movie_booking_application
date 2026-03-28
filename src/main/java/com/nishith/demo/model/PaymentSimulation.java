package com.nishith.demo.model;

import jakarta.persistence.*;

@Entity
public class PaymentSimulation {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name="id")
    private Booking bookingid;
    private int ammount_in_rs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
