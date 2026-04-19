package com.nishith.demo.service;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.PaymentSimulation;
import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.model.Shows;
import com.nishith.demo.repo.BookingsRepo;
import com.nishith.demo.repo.PaymentRepo;
import com.nishith.demo.repo.SeatSelectionRepo;

import com.nishith.demo.repo.ShowRepo;
import jakarta.transaction.Transactional;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Autowired
    private ShowRepo srepo;

    public List<Booking> getallbookings() {
       return brepo.findAll();
    }

    public Booking getbyid(int id) {
        return brepo.findById(id).orElseThrow();
    }





    @Transactional
    public String bookAticket(Booking booking){
        Random random =new Random();
        int rand=100000+random.nextInt(99999);
        String captcha=String.valueOf(rand);
        List<String>seats =booking.getSeat();

     if(seats.size()>10){
         return "only less then 10 seats are allowed for Booking!!!";
     }
    int sh= booking.getShow().getId();
        for (String s : seats) {
            SeatSelection entity=ssrepo.findByShowid_IdAndSeat(sh, s);
            if (!(entity==null)) {
                long time = ChronoUnit.SECONDS.between(entity.getLockedAt(),LocalDateTime.now());
                if(time>300 &&entity.getStatus().equals("LOCKED")) {
                    entity.setStatus("AVAILABLE");
                    ssrepo.save(entity);}

                    if (entity.getStatus().equals("LOCKED") || entity.getStatus().equals("BOOKED")) {
                        return "The seats" + entity.getSeat() + "are not available";
                    }

            }
        }
        for(String s:seats){
           SeatSelection seatSelection=ssrepo.findByShowid_IdAndSeat(sh,s);
            seatSelection.setStatus("LOCKED");
            seatSelection.setLockedAt(LocalDateTime.now());
            seatSelection.setBooking(booking);
            ssrepo.save(seatSelection);
        }
        booking.setBookingdate(LocalDateTime.now());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        booking.setBookedBy(username);
        booking.setStatus("HOLD");
        brepo.save(booking);

        SeatSelection seatSelection=new SeatSelection();
        int total=seats.size()*seatSelection.getPrice();

        PaymentSimulation payment =new PaymentSimulation();
        payment.setAmmount_in_rs(total);
        payment.setStatus("HOLD");
        payment.setBookingtime(LocalDateTime.now());
        payment.setBookingid(booking);
        payment.setCaptcha(captcha);
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

            PaymentSimulation paymentSimulation =prepo.findByBookingid_BookingId(payment.getBookingid().getBookingId());

       if(cap.equals(paymentSimulation.getCaptcha()) &&  payment.getAmmount_in_rs()==paymentSimulation.getAmmount_in_rs())
       {
           paymentSimulation.setStatus("SUCCESS");
           prepo.save(paymentSimulation);
          Booking booking=brepo.findByPayment(payment);
          booking.setStatus("SUCCESS");
          brepo.save(booking);
          List<SeatSelection> ss=ssrepo.findByBooking(booking);
           for (SeatSelection seat : ss) {
               seat.setStatus("BOOKED");
               ssrepo.save(seat);
           }
           return "Booking was success with booking reference : " +booking.getBookingId();
        }
        }
        return "Payment details are invalid please retry with valid details !!";


    }
public String CancelBooking(int bookingId){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Booking booking=brepo.findById(bookingId).orElseThrow();
        Shows show=srepo.findById(booking.getShow().getId()).orElseThrow();
        List<SeatSelection> seatSelection=ssrepo.findByBooking(booking);
        long timediff=ChronoUnit.SECONDS.between(LocalDateTime.now(),show.getTimings());
        if(username.equals(booking.getBookedBy())) {
            if (timediff > 10800) {
                for(SeatSelection s:seatSelection){
                    s.setStatus("AVAILABLE");
                    s.setBooking(null);
                    s.setLockedAt(null);
                    ssrepo.save(s);
                }
                List<String> seats = booking.getSeat();
                booking.setStatus("CANCELED");
                PaymentSimulation payment = prepo.findByBookingid_BookingId(bookingId);
                payment.setStatus("CANCELED");
                payment.setBookingid(booking);
                brepo.save(booking);
                prepo.save(payment);
                return "Booking cancellation was success";
            }
            return "cancellation only allowed three hours before show time";
        }
        return "username mismatch";

}

    public List<SeatSelection> getSeatsByShowid(int showid) {
        return ssrepo.findByShowid_Id(showid);
    }

    public List<Booking> getBookingByUsername() {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
       return brepo.findByBookedBy(username);
    }
}
