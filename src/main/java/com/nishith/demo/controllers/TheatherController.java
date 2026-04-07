package com.nishith.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nishith.demo.model.Theather;
import com.nishith.demo.service.TheatherService;

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
	public void addTheather(@RequestBody Theather data)
	{
		service.addtheather(data);
	}
	@DeleteMapping("/theatres")
	public String DeleteTheather(@RequestBody Theather theather)
	{
		return service.delete(theather);
	}
@GetMapping("/theather/{location}")
	public List<Theather> grtByLocation(@PathVariable String location){
		return service.getByLocation(location);
}
}
