package com.nishith.demo.service;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.PaymentSimulation;
import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.model.Shows;
import com.nishith.demo.repo.BookingsRepo;
import com.nishith.demo.repo.PaymentRepo;
import com.nishith.demo.repo.SeatSelectionRepo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Service
public class BookingsService {
    @Autowired
    private BookingsRepo brepo;
    @Autowired
    private SeatSelectionRepo ssrepo;
    @Autowired
    private PaymentRepo prepo;

    public List<Booking> getallbookings() {
       return brepo.findAll();
    }

    public Booking getbyid(int id) {
        return brepo.findById(id).orElseThrow();
    }

    Random random =new Random();
    int rand=100000+random.nextInt(99999);
    String captcha=String.valueOf(rand);



    @Transactional
    public String bookAticket(Booking booking){
        List<String>seats =booking.getSeat();

     if(seats.size()>10){
         return "only less then 10 seats are allowed for Booking!!!";
     }
    Shows sh= booking.getShow();
        for (String s : seats) {
            List<SeatSelection> entity=ssrepo.findByShowidAndSeat(sh, s);
            if (!entity.isEmpty()) {
                long time = ChronoUnit.SECONDS.between(entity.getFirst().getLockedAt(),LocalDateTime.now());
                if(time>=300 &&entity.getFirst().getStatus().equals("LOCKED")) {
                    entity.getFirst().setStatus("AVAILABLE");
                    ssrepo.save(entity.getFirst());}

                    if (entity.getFirst().getStatus().equals("LOCKED") || entity.getFirst().getStatus().equals("BOOKED")) {
                        return "The seats" + entity.getFirst().getSeat() + "are not available";
                    }

            }
        }
        for(String s:seats){
           List<SeatSelection> entity=ssrepo.findByShowidAndSeat(sh,s);
            SeatSelection seatSelection=entity.getFirst();
            seatSelection.setStatus("LOCKED");
            seatSelection.setLockedAt(LocalDateTime.now());
            seatSelection.setBooking(booking);
            ssrepo.save(seatSelection);
        }
        booking.setBookingdate(LocalDateTime.now());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        booking.setBookedBy(username);
        booking.setStatus("HOLD");
        booking.setBookingdate(LocalDateTime.now());
        brepo.save(booking);

        SeatSelection seatSelection=new SeatSelection();
        int total=seats.size()*seatSelection.getPrice();

        PaymentSimulation payment =new PaymentSimulation();
        payment.setAmmount_in_rs(total);
        payment.setStatus("HOLD");
        payment.setBookingtime(LocalDateTime.now());
        payment.setBookingid(booking);
        prepo.save(payment);
        return "Your Total Payment of : "+total +" .Complete the payment to Conform booking reference id : "+booking.getBookingId()+
                " please enter captcha along with total Amount "+captcha;

    }
    @Transactional
    public String afterPayment(PaymentSimulation payment ,String cap) {
        if (!(payment == null)) {
            LocalDateTime paymentTime = payment.getBookingtime();
            long timeDiff = ChronoUnit.SECONDS.between(paymentTime, LocalDateTime.now());
            if (timeDiff > 300) {
                return "Payment failed due to over Late Payment try to book again to continue";
            }

            PaymentSimulation paymentSimulation =prepo.findBybookingid(payment.getBookingid());

       if(cap.equals(captcha) && !(payment.getBookingid()==paymentSimulation.getBookingid()) && payment.getAmmount_in_rs()==paymentSimulation.getAmmount_in_rs())
       {
           paymentSimulation.setStatus("SUCCESS");
           prepo.save(paymentSimulation);
          Booking booking=brepo.findBypayment(payment);
          booking.setStatus("SUCCESS");
          brepo.save(booking);
          List<SeatSelection> ss=ssrepo.findBybooking(booking);
           for (SeatSelection seat : ss) {
               seat.setStatus("BOOKED");
               ssrepo.save(seat);
           }
           return "Booking was success with booking reference : " +booking.getBookingId();
        }
        }
        return "Payment details are invalid please retry with valid details !!";


    }


    }
