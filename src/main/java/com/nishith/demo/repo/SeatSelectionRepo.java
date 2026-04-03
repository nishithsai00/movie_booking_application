package com.nishith.demo.repo;

import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.model.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatSelectionRepo extends JpaRepository<SeatSelection,Integer> {
    List<SeatSelection> findByShowidAndSeat(Shows showid, String seat);
    List<SeatSelection> findByStatusAndLockedAtBefore(String seat, LocalDateTime date);
}
