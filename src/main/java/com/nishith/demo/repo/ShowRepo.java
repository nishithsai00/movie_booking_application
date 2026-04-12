package com.nishith.demo.repo;

import com.nishith.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nishith.demo.model.Shows;

import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<Shows, Integer> {
 void deleteByMovie_IdAndTheather_Id(int movie, int theather);
 List<Shows> findByTheather_LocationAndMovie_Name(String loc, String movie);

 @Query("SELECT s.movie FROM Shows s WHERE s.theather.location = :location")
 List<Movie> findMoviesByLocation(String location);
}
