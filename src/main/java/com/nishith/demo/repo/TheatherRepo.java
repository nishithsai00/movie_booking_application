package com.nishith.demo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nishith.demo.model.Theather;
@Repository
public interface TheatherRepo extends JpaRepository<Theather, Integer>{

}
