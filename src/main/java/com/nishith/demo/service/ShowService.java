package com.nishith.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nishith.demo.model.Movie;
import com.nishith.demo.model.SeatSelection;
import com.nishith.demo.repo.SeatSelectionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nishith.demo.model.Shows;
import com.nishith.demo.repo.ShowRepo;

@Service
public class ShowService {
	@Autowired
	ShowRepo srepo;
	@Autowired
	SeatSelectionRepo ssrepo;

	@Transactional
public String createExistingShow(int id,LocalDateTime date){
	Shows s=srepo.findById(id).orElse(null);
    s.setTimings(date);
	srepo.save(s);
	List<SeatSelection> entity=ssrepo.findByShowid_Id(id);
	for(SeatSelection seat:entity){
		seat.setBooking(null);
		seat.setShowid(s);
		seat.setStatus("AVAILABLE");
		seat.setLockedAt(null);
		ssrepo.save(seat);
	}
	return "the show is updated successfully";
}

	public List<Shows> getall() {
		return srepo.findAll();
	}
     @Transactional
	public int addshow(Shows sh) {
		srepo.save(sh);

		for(char i='A';i<'M';i++){
			for(int j=1;j<9;j++){
				SeatSelection seat=new SeatSelection();
              seat.setShowid(sh);
			  seat.setSeat(i+""+j);
			  ssrepo.save(seat);
			}
		}

		return sh.getId();
	}
     public String deleteShowById(int id){
		srepo.deleteById(id);
		return  "Show deleted succesfully";
	 }

    public void deleteAshow(int movieId, int theatherId) {
		srepo.deleteByMovie_IdAndTheather_Id(movieId,theatherId);
    }



	//get movies by location
    public List<Movie> getMovieByLocation(String location) {
		return srepo.findMoviesByLocation(location);
    }
	public List<Shows> getShowsbyMovienameAndLocation(String name ,String location){

      return srepo.findByTheather_LocationAndMovie_Name(name,location);
	}
}
