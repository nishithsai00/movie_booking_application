package com.nishith.reserveShow.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Booking {

    public Booking() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;
    @ManyToOne
    @JoinColumn(name = "showid")
    private  Shows show;
    @ManyToOne
    @JoinColumn(name="paymentid")
    private PaymentSimulation payment;
    @ElementCollection
    private List<String> seat;
    private String status;
    private String bookedBy;
    private LocalDateTime Bookingdate;

    public PaymentSimulation getPayment() {
        return payment;
    }

    public void setPayment(PaymentSimulation payment) {
        this.payment = payment;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Shows getShow() {
        return show;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShow(Shows show) {
        this.show = show;
    }

    public List<String> getSeat() {
        return seat;
    }

    public void setSeat(List<String> seat) {
        this.seat = seat;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public LocalDateTime getBookingdate() {
        return Bookingdate;
    }

    public void setBookingdate(LocalDateTime bookingdate) {
        Bookingdate = bookingdate;
    }
}
