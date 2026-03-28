package com.nishith.demo.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nishith.demo.model.Movie;
import com.nishith.demo.repo.MovieRepo;
import com.nishith.demo.specfication.Moviespecification;
@Service
public class MovieService {
	@Autowired
	MovieRepo mrepo;

	public List<Movie> allmovies() {
		return mrepo.findAll() ;
	}

	public void addmovie(Movie movie) {
		mrepo.save(movie);
		
	}

	public Movie getbyid(int num) {
		
		return mrepo.findById(num).orElseThrow(()->new MovieNotFoundException(num));
	}

	public void updatemovie(int id, Movie m) {
		m.setId(id);
		mrepo.save(m);
		
	}

	public String addm(Movie m, MultipartFile img){
		
		m.setImgname(img.getOriginalFilename());
		m.setImgtype(img.getContentType());
		try {
		m.setImage(img.getBytes());
		mrepo.save(m);
		return "added succes";
		
		}
		catch(Exception e) {
			return "not found";
		}
		
		
				
				
	}

	public void adddata(Movie m) {
	    mrepo.save(m);
		
	}

	public List<Movie> byname(String name) {
		
		return mrepo.findByName(name);
	}
    
	public List<Movie> sort(Integer id, String name, LocalDate date) {
		Specification<Movie> spec =Specification.allOf();
		if(id!=null)
			spec= spec.and(Moviespecification.byid(id));
		if(name!=null)
			 spec=spec.and(Moviespecification.name(name));
		if(date!=null)
			 spec=spec.and(Moviespecification.date(date));
		
		return mrepo.findAll(spec);
			
	}

}
