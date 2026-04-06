package com.nishith.demo.controllers;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nishith.demo.model.Shows;
import com.nishith.demo.service.ShowService;

@RestController
public class ShowController {
	@Autowired
	ShowService service;
	@GetMapping("/shows")
	public List<Shows> allshows()
	{
		return service.getall();
	}
@PostMapping("/shows")
public void addshow(@RequestBody Shows sh)
{
	service.addshow(sh);
}
@DeleteMapping("/removeshow")
	public String removeAshow(@RequestBody int movieId,int theatherId ){
		service.deleteAshow(movieId,theatherId);
		return "Show successfully removed";

}
}
