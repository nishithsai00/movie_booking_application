package com.nishith.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.demo.model.Shows;
import com.nishith.demo.repo.ShowRepo;

@Service
public class ShowService {
	@Autowired
	ShowRepo srepo;

	public List<Shows> getall() {
		return srepo.findAll();
	}

	public void addshow(Shows sh) {
		srepo.save(sh);
		
	}
	

}
