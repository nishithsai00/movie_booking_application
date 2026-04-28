package com.nishith.reserveShow.autoScheduling;

import com.nishith.reserveShow.model.SeatSelection;
import com.nishith.reserveShow.repo.SeatSelectionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AutoScheduling {
    @Autowired
    SeatSelectionRepo ssrepo;


    @Scheduled(fixedRate = 60000)
    @Transactional
    public void ReleaseSeats(){
        List<SeatSelection> seatSelections=ssrepo.findBySeatStatus("LOCKED");
        for(SeatSelection seat:seatSelections){
            long time = ChronoUnit.SECONDS.between(seat.getLockedAt(), LocalDateTime.now());
            if(time>300){
                seat.setStatus("AVAILABLE");
                ssrepo.save(seat);
            }

        }
    }
}
