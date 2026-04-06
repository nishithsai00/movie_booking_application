package com.nishith.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nishith.demo.model.Theather;

import java.util.List;

@Repository
public interface TheatherRepo extends JpaRepository<Theather, Integer>{
    List<Theather> findByLocation(String location);

}
