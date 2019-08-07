package com.dat.book_management.controllers;

import com.dat.book_management.configurations.TokenProvider;
import com.dat.book_management.models.AuthToken;
import com.dat.book_management.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwrTokenUtil;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login){
        String email = login.getEmail();
        String password = login.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwrTokenUtil.generateToken(authentication);

        return ResponseEntity.ok(new AuthToken(token));
    }

    @DeleteMapping
    public void logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}