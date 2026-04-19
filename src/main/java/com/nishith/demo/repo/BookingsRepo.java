package com.nishith.demo.repo;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.PaymentSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepo extends JpaRepository<Booking,Integer> {
  Booking findByPayment(PaymentSimulation paymentSimulation);
  List<Booking> findByBookedBy(String username);

}
