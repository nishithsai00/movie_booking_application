package com.nishith.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nishith.demo.model.Shows;

@Repository
public interface ShowRepo extends JpaRepository<Shows, Integer> {

}
