package com.nishith.demo.service;

import com.nishith.demo.model.Booking;
import com.nishith.demo.repo.BookingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingsService {
    @Autowired
    private BookingsRepo brepo;


    public List<Booking> getallbookings() {
       return brepo.findAll();
    }

    public Booking getbyid(int id) {
        return brepo.findById(id).orElseThrow();
    }

    public int bookAticket(Booking booking) {
       return brepo.save(booking)
                  .getId();

    }

}
