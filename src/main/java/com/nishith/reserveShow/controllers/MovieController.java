package com.nishith.reserveShow.controllers;


import java.time.LocalDate;
import java.util.List;

import com.nishith.reserveShow.exceptionHandler.MovieNotFoundException;
import com.nishith.reserveShow.model.Shows;
import com.nishith.reserveShow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.nishith.reserveShow.model.Movie;
import com.nishith.reserveShow.service.MovieService;

@RestController
@CrossOrigin
public class MovieController {
	@Autowired
	MovieService service;
	@Autowired
	ShowService showService;

	
	@GetMapping("/movies")
	public ResponseEntity<List<Movie>>allmovies()
	{
		
		return new ResponseEntity(service.allmovies(),HttpStatus.OK);
	}
//	@PostMapping("/movies")
//	@PreAuthorize("hasRole('ADMIN')")
//	public ResponseEntity<Movie> addmovie(@RequestBody Movie movie )
//	{
//		service.addmovie(movie);
//		return new ResponseEntity(HttpStatus.OK);
//	}
	@GetMapping("/movies/{num}")
	public ResponseEntity<Movie> getbyid(@PathVariable int num)
	{
		Movie m=service.getbyid(num);
		if(m!=null)
			return new ResponseEntity( m,HttpStatus.OK);
		else
			throw new MovieNotFoundException(num);
//			return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	@PutMapping("/movie/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> updatemovie(@PathVariable int id,@RequestBody Movie m)
	{
		service.updatemovie(id,m);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/addmovie")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> addm(@RequestPart Movie m,@RequestPart(required =false) MultipartFile img)
	{if (img==null)
	{
		service.adddata(m);
	}
	else {
		service.addm(m,img);
	}
	return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@GetMapping("/movie/{id}/image")
	public ResponseEntity<byte[]> getimg(@PathVariable int id)
	{
		Movie m=service.getbyid(id);
	  byte[] b=m.getImage();
	    return ResponseEntity.ok()
	    		.contentType(MediaType.valueOf(m.getImgtype()))
	    		.body(b);
	}
	@GetMapping("/movie/name/{name}")
	public ResponseEntity<Movie> byname(@PathVariable String name)
	{
		return new ResponseEntity(service.byname(name),HttpStatus.OK);
	}
	@GetMapping("/movie/sort")
	public List<Movie> sort(@RequestParam (required =false)Integer id ,@RequestParam (required=false) String name,@RequestParam (required=false) LocalDate date)
	{
		return service.sort(id,name,date);
	}
	@GetMapping("/movie/location/{location}")
	public ResponseEntity<List<Movie>> getByLocation(@PathVariable String location){
		 return new ResponseEntity<List<Movie>>(showService.getMovieByLocation(location),HttpStatus.OK);
	}
	@GetMapping("/movie/{location}/{name}")
	public ResponseEntity<List<Shows>> getByMovienameAndLocation(@PathVariable String name ,@PathVariable String location){
		return new ResponseEntity<List<Shows>>(showService.getShowsbyMovienameAndLocation(name,location),HttpStatus.OK);
	}
}
