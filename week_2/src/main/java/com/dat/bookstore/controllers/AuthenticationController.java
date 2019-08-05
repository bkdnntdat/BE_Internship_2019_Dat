package com.dat.bookstore.controllers;

import com.dat.bookstore.configurations.TokenProvider;
import com.dat.bookstore.dao.Login;
import com.dat.bookstore.models.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwrTokenUtil;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login){
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwrTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }
}
