package com.nishith.demo.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Booking {

    public Booking() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "masterid")
    private  Shows showid;

//    @ElementCollection
    private String seat;

   // private String status;
    private int bookedBy;
    private Date Bookingdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeats() {
        return seat;
    }

    public void setSeats(String seats) {
        this.seat = seats;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Date getBookingdate() {
        return Bookingdate;
    }

    public void setBookingdate(Date bookingdate) {
        Bookingdate = bookingdate;
    }


    public Shows getShows() {
        return showid;
    }

    public void setShows(Shows showid) {
        this.showid = showid;
    }

    public Booking(Shows shows)
    {
        this.showid= shows;
    }
}
