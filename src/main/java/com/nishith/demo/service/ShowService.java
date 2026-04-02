package com.nishith.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.repo.SeatSelectionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.demo.model.Shows;
import com.nishith.demo.repo.ShowRepo;

@Service
public class ShowService {
	@Autowired
	ShowRepo srepo;
	@Autowired
	SeatSelectionRepo ssrepo;

	public List<Shows> getall() {
		return srepo.findAll();
	}
     @Transactional
	public void addshow(Shows sh) {
		srepo.save(sh);

		for(char i='A';i<'M';i++){
			for(int j=1;j<9;j++){
				SeatSelection seat=new SeatSelection();
              seat.setShowid(sh);
			  seat.setSeat(i+""+j);
			  ssrepo.save(seat);
			}
		}

		
	}
	

}
