package com.nishith.demo.controllers;

import com.nishith.demo.model.Users;
import com.nishith.demo.service.JwtService;
import com.nishith.demo.service.MyUserDetailsService;
import com.nishith.demo.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
public class UserController {
    @Autowired
    UserAuthService service;
    @Autowired
    JwtService ser;

    @PostMapping("/signup")
    public String signup(@RequestBody Users u) {
        u.setRole("USER");
        service.register(u);
        return "Done";
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        System.out.println(user);
        return service.verifylogin(user);

    }

    @PostMapping("/admin/createadmin")
    @PreAuthorize("hasRole('ADMIN')") // this allows only admin creates new admin
    public String admincreation(@RequestBody Users users) {
        users.setRole("ADMIN");
        service.register(users);
        return "admin creation success";
    }
}