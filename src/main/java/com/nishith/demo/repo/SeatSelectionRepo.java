package com.nishith.demo.repo;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.SeatSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatSelectionRepo extends JpaRepository<SeatSelection,Integer> {
    SeatSelection findByShowid_IdAndSeat(int showid, String seat);
    List<SeatSelection> findByBooking(Booking booking);
}
