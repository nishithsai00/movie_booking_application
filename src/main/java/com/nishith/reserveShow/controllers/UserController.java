package com.nishith.reserveShow.controllers;

import com.nishith.reserveShow.model.Users;


import com.nishith.reserveShow.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {
    @Autowired
    UserAuthService service;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody Users u) {
        u.setRole("USER");
        service.register(u);
        return new ResponseEntity<String>( "Done",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
       String token= service.verifylogin(user);
        return new ResponseEntity<String>("Login Done with token : "+token,HttpStatus.OK);

    }

    @PostMapping("/admin/createadmin")
    @PreAuthorize("hasRole('ADMIN')") // this allows only admin creates new admin
    public ResponseEntity<String> admincreation(@Valid @RequestBody Users users) {
        users.setRole("ADMIN");
        service.register(users);
        return new ResponseEntity<String>("admin creation success with username : " +users.getUsername(),HttpStatus.CREATED);
    }
}