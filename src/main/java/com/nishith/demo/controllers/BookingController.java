package com.nishith.demo.controllers;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.PaymentSimulation;
import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingsService bservice;


    @GetMapping("/mybookings")
    public List<Booking> getBookingByUsername(){
       return bservice.getBookingByUsername();
    }

    @GetMapping("/allbook")
   @PreAuthorize("hasRole('ADMIN')")
    public List<Booking> getallmovies()
    {
        return bservice.getallbookings();
    }

    @GetMapping("/book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Booking getbyid(@PathVariable int id)
    {
        return bservice.getbyid(id);
    }
    @GetMapping("/getshows")
    public List<SeatSelection> showSeatsByShowid(int showid){
       return bservice.getSeatsByShowid(showid);
    }



    @PostMapping("/book")
    public String bookaticket(@RequestBody Booking booking)
    {

      return bservice.bookAticket(booking);
    }
    @PostMapping("/payment")
    public String paymentCheck(@RequestBody  PaymentSimulation paymentSimulation, @RequestParam String captcha){
        return bservice.afterPayment(paymentSimulation,captcha);
    }
}

