package com.nishith.reserveShow.controllers;

import com.nishith.reserveShow.model.Booking;
import com.nishith.reserveShow.model.PaymentSimulation;
import com.nishith.reserveShow.model.SeatSelection;
import com.nishith.reserveShow.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookingController {

    @Autowired
    BookingsService bservice;


    @GetMapping("/mybookings")
    public ResponseEntity<List<Booking>> getBookingByUsername(){
       return new ResponseEntity<List<Booking>>(bservice.getBookingByUsername(),HttpStatus.OK) ;
    }

    @GetMapping("/allbookings")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getallmovies()
    {
        return new ResponseEntity<List<Booking>>(bservice.getAllBookings(), HttpStatus.OK);
    }

    @GetMapping("/booking/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Booking> getbyid(@PathVariable int id)
    {
        return new ResponseEntity<Booking>(bservice.getById(id),HttpStatus.OK);
    }
    @GetMapping("/movie/{showid}")
    public ResponseEntity<List<SeatSelection>> showSeatsByShowid(@PathVariable int showid){
       return new ResponseEntity<List<SeatSelection>>(bservice.getSeatsByShowid(showid),HttpStatus.OK);
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

