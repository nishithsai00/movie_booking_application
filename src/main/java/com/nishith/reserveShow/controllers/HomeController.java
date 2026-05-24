package com.nishith.reserveShow.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {
    @RequestMapping("/")
    public String urls(){
        return "please do vist the below url to test api's -> http://localhost:8080/swagger-ui.html " ;
    }
}
