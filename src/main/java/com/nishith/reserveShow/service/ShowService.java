package com.nishith.reserveShow.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nishith.reserveShow.exceptionHandler.EmptyListException;
import com.nishith.reserveShow.model.Movie;
import com.nishith.reserveShow.model.SeatSelection;
import com.nishith.reserveShow.model.Status;
import com.nishith.reserveShow.repo.SeatSelectionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.reserveShow.model.Shows;
import com.nishith.reserveShow.repo.ShowRepo;

@Service
public class ShowService {
	@Autowired
	ShowRepo srepo;
	@Autowired
	SeatSelectionRepo ssrepo;

	@Transactional
public String createExistingShow(int id,LocalDateTime date){
	Shows oldShow=srepo.findById(id).orElseThrow(()->new EmptyListException("show not found with the id :"+id));
	Shows newShow=new Shows();
	newShow.setTimings(date);
	newShow.setMovie(oldShow.getMovie());
	newShow.setTheather(oldShow.getTheather());
	srepo.save(newShow);

	List<SeatSelection> oldSeats=ssrepo.findByShowid_Id(id);
	List<SeatSelection> newSeats=new ArrayList<>();
	for(SeatSelection oldSeat:oldSeats){
		SeatSelection seat=new SeatSelection();
		seat.setBooking(null);
		seat.setSeat(oldSeat.getSeat());
		seat.setShowid(newShow);
		seat.setStatus(Status.AVAILABLE);
		seat.setLockedAt(null);
		newSeats.add(seat);
	}
	ssrepo.saveAll(newSeats);
	return "the show is updated successfully";
}

	public List<Shows> getall() {
		return srepo.findAll();
	}
     @Transactional
	public int addshow(Shows sh) {
		srepo.save(sh);
List<SeatSelection> seats=new ArrayList<>();
		for(char i='A';i<'M';i++){
			for(int j=1;j<9;j++){
				SeatSelection seat=new SeatSelection();
              seat.setShowid(sh);
			  seat.setSeat(i+""+j);
			 seats.add(seat);
			}
		}
		ssrepo.saveAll(seats);

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
