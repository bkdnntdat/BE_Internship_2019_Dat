package com.dat.book_management.controllers;

import com.dat.book_management.models.Login;
import com.dat.book_management.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody Login login){
        return authenticationService.login(login);
    }

    @DeleteMapping
    public void logout(){
        authenticationService.logout();
    }
}