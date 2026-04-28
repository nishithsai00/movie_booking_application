package com.nishith.reserveShow.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String urls(){
        return "ssssrg" +
                "rsg" +
                "srgrs" +
                "gsgrrs,"+
                "grsg" +
                "rsgrsg" +
                "srgrsg" +
                "srgsrgsr" +
                "grsgrsg" ;
    }
}
