package com.dat.blog.controllers;

import com.dat.blog.repositories.CommentRepository;
import com.dat.blog.repositories.RoleRepository;
import com.dat.blog.repositories.UserRepository;
import com.dat.blog.role.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.TimerTask;

@Secured("ROLE_ADMIN")
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "/setRoleAdmin", method = RequestMethod.GET)
    public String setRoleAdmin(@RequestParam String username){
        User user = userRepository.findByUsername(username);
        user.setRole(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
        return "redirect:/userManager";
    }

//    @RequestMapping(value = "/setRoleUser", method = RequestMethod.GET)
//    public String setRoleUser(@RequestParam String username){
//        User user = userRepository.findByUsername(username);
//        user.setRole(roleRepository.findByName("ROLE_MEMBER"));
//        return "redirect:/home";
//    }
}
