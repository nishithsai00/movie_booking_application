package com.nishith.demo.service;

import com.nishith.demo.model.Users;
import com.nishith.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService
{
    @Autowired
    UserRepo repo;
    @Autowired
            JwtService jwtService;

 BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
public void register(Users u)
{
    u.setPassword(encoder.encode(u.getPassword()));
    repo.save(u);


}
@Autowired
AuthenticationManager authmanager;
public String verifylogin(Users user) {
    Authentication authentication =authmanager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
    if(authentication.isAuthenticated()) {
        Users u = repo.findByUsername(user.getUsername());
        return jwtService.tokengeneration(u);
    }
    else {
        throw  new RuntimeException("user not found");
    }

}
}
