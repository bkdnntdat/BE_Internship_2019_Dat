package com.dat.book_management.controllers;

import com.dat.book_management.models.Login;
import com.dat.book_management.repositories.RoleRepository;
import com.dat.book_management.repositories.UserRepository;
import com.dat.book_management.roles.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    AuthenticationController authenticationController;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody User user){
        if(userRepository.findByEmail(user.getEmail()) !=null){
            return null;
        }
        user.setRole(roleRepository.findByName("ROLE_MEMBER"));

        Login login = new Login();

        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());

        StringBuilder builder = new StringBuilder();

        for(int i=0; i<6; i++){
            builder.append(new Random().nextInt(10));
        }

        user.setCode(builder.toString());

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        user = userRepository.save(user);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("[Mã xác nhận]");
        message.setText("Mã xác nhận của bạn là: " + user.getCode());

        mailSender.send(message);

        return authenticationController.login(login);
    }
}
