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
    private int bookingId;
    @ManyToOne
    @JoinColumn(name = "masterid")
    private  Shows showid;

    public int getBookingId() {
        return bookingId;
    }

    @ElementCollection
    private List<String> seat;

   // private String status;
    private int bookedBy;
    private Date Bookingdate;

    public int getId() {
        return bookingId;
    }

    public void setId(int id) {
        this.bookingId = id;
    }

    public Shows getShowid() {
        return showid;
    }

    public void setShowid(Shows showid) {
        this.showid = showid;
    }

    public List<String> getSeat() {
        return seat;
    }

    public void setSeat(List<String> seat) {
        this.seat = seat;
    }

    public List<String> getSeats() {
        return seat;
    }

    public void setSeats(List<String> seats) {
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
