package com.nishith.demo.controllers;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ADMIN')")
public String addshow(@RequestBody Shows sh)
{
	return "Show created with reference id" + service.addshow(sh);
}
@DeleteMapping("/removeshow")
@PreAuthorize("hasRole('ADMIN')")
	public String removeAshow(@RequestBody int movieId,int theatherId ){
		service.deleteAshow(movieId,theatherId);
		return "Show successfully removed";
	}

}
