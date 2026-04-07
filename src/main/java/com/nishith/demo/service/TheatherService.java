package com.nishith.demo.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nishith.demo.model.Theather;
import com.nishith.demo.repo.TheatherRepo;

@Service
public class TheatherService {
	@Autowired
	TheatherRepo trepo;
	public List<Theather> alltheathers()
	{
		return trepo.findAll();
	}
	public void addtheather(Theather data) {
		trepo.save(data);
	}

    public String delete(Theather theather) {
		try {
			trepo.deleteById(theather.getId());
			return "deleted by entity";
		}
		catch (Exception e){
			trepo.delete(theather);
			return "deleted by id";
		}
    }

	public List<Theather> getByLocation(String location) {
		return trepo.findByLocation(location);
	}
}
