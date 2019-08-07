package com.dat.book_management.controllers;

import com.dat.book_management.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {
    @Autowired
    AuthenticationController authenticationController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String page(){
        return "index.html";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "home.html";
    }
}
