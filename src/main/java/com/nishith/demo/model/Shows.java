package com.nishith.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.apache.logging.log4j.CloseableThreadContext;

import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Entity
public class Shows {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int masterid;
@JsonFormat(pattern ="dd-MM-yyyy HH:mm:ss")
private LocalDateTime timings;

@ManyToOne
@JoinColumn(name="movieid")
private Movie movie;

	public LocalDateTime getTimings() {
		return timings;
	}

	public void setTimings(LocalDateTime timings) {
		this.timings = timings;
	}

	public int getMasterid() {
		return masterid;
	}

	@ManyToOne
@JoinColumn(name="theatherid")
private Theather theather;
public int getId() {
	return masterid;
}
public void setId(int id) {
	this.masterid = id;
}
public Movie getMovie() {
	return movie;
}
public void setMovie(Movie movieid) {
	this.movie = movieid;
}
public Theather getTheather() {
	return theather;
}
public void setTheather(Theather theatherid) {
	this.theather = theatherid;
}
	
	public Shows()
	{
		
	}
	public Shows(Movie movieid, Theather theatherid, LocalDateTime timings) {
		super();
		this.movie = movieid;
		this.theather = theatherid;
		this.timings=timings;
	}
}
