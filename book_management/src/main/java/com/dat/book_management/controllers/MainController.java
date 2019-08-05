package com.dat.book_management.controllers;

import com.dat.book_management.models.Login;
import com.dat.book_management.repositories.RoleRepository;
import com.dat.book_management.repositories.UserRepository;
import com.dat.book_management.roles.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {
    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String page(){
        return "index.html";
    }

    @RequestMapping(value = "/logining", method = RequestMethod.POST)
    public String logining(@RequestBody Login login){

        authenticationController.login(login);

        return login.getEmail();
    }

//    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        user.setRole(roleRepository.findByName("ROLE_MEMBER"));

        userRepository.save(user);

        return user.toString();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
        return "home.html";
    }
}
