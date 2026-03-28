package com.nishith.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nishith.demo.model.Movie;
import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> , JpaSpecificationExecutor<Movie>{
 public List<Movie> findByName(String name);
 
}
