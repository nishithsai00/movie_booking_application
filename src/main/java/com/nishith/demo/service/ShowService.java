package com.nishith.demo.service;

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


    public void deleteAshow(int movieId, int theatherId) {
		srepo.deleteByMovie_IdAndTheather_Id(movieId,theatherId);
    }


	//get movies by location
    public List<Movie> getMovieByLocation(String location) {
		List<Shows> entity=srepo.findByTheather_Location(location);
		List<Movie>movies=new ArrayList();
		for(Shows m:entity){
			movies.add(m.getMovie());
		}
		 return movies;
    }
	public List<Shows> getShowsbyMovienameAndLocation(String name ,String location){

      return srepo.findByTheather_LocationAndMovie_Name(name,location);
	}
}
