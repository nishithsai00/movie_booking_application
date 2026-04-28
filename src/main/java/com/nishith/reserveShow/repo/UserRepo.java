package com.nishith.reserveShow.repo;

import com.nishith.reserveShow.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users ,Integer>
{
    Users findByUsername(String username);
}
