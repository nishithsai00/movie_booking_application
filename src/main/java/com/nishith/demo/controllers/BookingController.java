package com.nishith.demo.controllers;

import com.nishith.demo.model.Booking;
import com.nishith.demo.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingsService bservice;

    @GetMapping("/book")
    public List<Booking> getallmovies()
    {
        return bservice.getallbookings();
    }
    @GetMapping("/book/{id}")
    public Booking getbyid(@PathVariable int id)
    {
        return bservice.getbyid(id);
    }
    @PostMapping("/book")
    public String bookaticket(@RequestBody Booking booking)
    {

      return "booking conformed with reference id : " +  bservice.bookAticket(booking);
    }
}

