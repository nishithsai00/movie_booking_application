package com.nishith.reserveShow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.nishith.reserveShow.model.Theather;
import com.nishith.reserveShow.service.TheatherService;

@RestController
@CrossOrigin
public class TheatherController {
	@Autowired
	TheatherService service;

	@GetMapping("/theatres")
	public List<Theather> AllTheather()
	{
		return service.alltheathers();
	}
	@PostMapping("/theatres")
	@PreAuthorize("hasRole('ADMIN')")
	public void addTheather(@RequestBody Theather data)
	{
		service.addtheather(data);
	}
	@PutMapping("/theatres/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String edit(@PathVariable int id,@RequestBody Theather theather){
		service.editById(id,theather);
		return "Success";
	}
	@DeleteMapping("/theatres")
	@PreAuthorize("hasRole('ADMIN')")
	public String DeleteTheather(@RequestBody Theather theather)
	{
		return service.delete(theather);
	}
@GetMapping("/theather/{location}")
	public List<Theather> grtByLocation(@PathVariable String location){
		return service.getByLocation(location);
}
}
                