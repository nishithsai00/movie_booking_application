package com.nishith.demo.repo;

import com.nishith.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users ,Integer>
{
    Users findByusername(String username);
}
