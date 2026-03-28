package com.nishith.demo.model;

import jakarta.persistence.*;

@Entity
public class BookingStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "id")
    private Booking bookid;
    private String status;


}
