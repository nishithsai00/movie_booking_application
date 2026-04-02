package com.nishith.demo.model;

import jakarta.persistence.*;

@Entity
public class BookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    public int getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking bookid;
    private String status;

    public Booking getBookid() {
        return bookid;
    }

    public void setBookid(Booking bookid) {
        this.bookid = bookid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
