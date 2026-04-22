package com.nishith.demo.controllers;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.PaymentSimulation;
import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {

    @Autowired
    BookingsService bservice;


    @GetMapping("/mybookings")
    public ResponseEntity<List<Booking>> getBookingByUsername(){
       return new ResponseEntity<List<Booking>>(bservice.getBookingByUsername(),HttpStatus.FOUND) ;
    }

    @GetMapping("/allbookings")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getallmovies()
    {
        return new ResponseEntity<List<Booking>>(bservice.getAllBookings(), HttpStatus.FOUND);
    }

    @GetMapping("/book/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> getbyid(@PathVariable int id)
    {
        return new ResponseEntity<Booking>(bservice.getById(id),HttpStatus.FOUND);
    }
    @GetMapping("/movie/{showid}")
    public ResponseEntity<List<SeatSelection>> showSeatsByShowid(@PathVariable int showid){
       return new ResponseEntity<List<SeatSelection>>(bservice.getSeatsByShowid(showid),HttpStatus.FOUND);
    }



    @PostMapping("/book")
    public ResponseEntity<String> bookaticket(@RequestBody Booking booking)
    {

      return new ResponseEntity<String>(bservice.bookAticket(booking),HttpStatus.ACCEPTED);
    }
    @PostMapping("/payment")
    public ResponseEntity<String> paymentCheck(@RequestBody  PaymentSimulation paymentSimulation, @RequestParam String captcha){
        return new ResponseEntity<>(bservice.afterPayment(paymentSimulation,captcha),HttpStatus.ACCEPTED);
    }
}

