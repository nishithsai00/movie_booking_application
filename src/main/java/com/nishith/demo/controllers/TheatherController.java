package com.nishith.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public void DeleteTheather()
	{
		
	}

}
