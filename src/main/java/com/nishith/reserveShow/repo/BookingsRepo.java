package com.nishith.reserveShow.repo;

import com.nishith.reserveShow.model.Booking;
import com.nishith.reserveShow.model.PaymentSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepo extends JpaRepository<Booking,Integer> {
  Booking findByPayment(PaymentSimulation paymentSimulation);
  List<Booking> findByBookedBy(String username);

}
