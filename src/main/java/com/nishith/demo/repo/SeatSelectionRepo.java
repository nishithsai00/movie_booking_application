package com.nishith.demo.repo;

import com.nishith.demo.model.Booking;
import com.nishith.demo.model.SeatSelection;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatSelectionRepo extends JpaRepository<SeatSelection,Integer> {
//    SeatSelection findByShowid_IdAndSeat(int showid, String seat);
    List<SeatSelection> findByBooking(Booking booking);
    List<SeatSelection> findByShowid_Id(int showid);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query ("SELECT s FROM SeatSelection s WHERE s.showid.id=:showid AND s.seat=:seat")
    SeatSelection findByShowid_IdAndSeat(@Param("showid") int id, @Param("seat") String seat);
    @Query("SELECT s FROM SeatSelection s WHERE s.status=:status")
    List<SeatSelection> findBySeatStatus(@Param("status") String status);
}
