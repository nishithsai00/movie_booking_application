package com.nishith.reserveShow.repo;

import com.nishith.reserveShow.model.PaymentSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo  extends JpaRepository<PaymentSimulation,Integer> {
    PaymentSimulation findByBookingid_BookingId(int booking);
}
