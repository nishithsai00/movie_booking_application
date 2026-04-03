package com.nishith.demo.model;

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
    @JoinColumn(name = "masterid")
    private  Shows show;
    @ElementCollection
    private List<String> seat;
    // private String status;
    private int bookedBy;
    private LocalDateTime Bookingdate;



    public int getBookingId() {
        return bookingId;
    }

    public Shows getShow() {
        return show;
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

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }

    public LocalDateTime getBookingdate() {
        return Bookingdate;
    }

    public void setBookingdate(LocalDateTime bookingdate) {
        Bookingdate = bookingdate;
    }
}
