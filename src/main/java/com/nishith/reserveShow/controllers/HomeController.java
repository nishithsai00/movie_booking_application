package com.nishith.reserveShow.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    @RequestMapping("/")
    public String urls(){
        return "please do vist the below url to test or view all api's -> https://movie-booking-application-8t3w.onrender.com/swagger-ui.html " ;
    }
}
