package com.nishith.demo.repo;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.PaymentSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo  extends JpaRepository<PaymentSimulation,Integer> {
    PaymentSimulation findBybookingid(Booking booking);
}
