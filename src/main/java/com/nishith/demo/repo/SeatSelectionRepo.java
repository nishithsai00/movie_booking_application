package com.nishith.demo.repo;

import com.nishith.demo.model.SeatSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatSelectionRepo extends JpaRepository<SeatSelection,Integer> {
}
