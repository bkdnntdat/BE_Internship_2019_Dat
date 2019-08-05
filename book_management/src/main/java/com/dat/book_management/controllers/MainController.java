package com.dat.book_management.controllers;

import com.dat.book_management.configurations.MailConfig;
import com.dat.book_management.models.Login;
import com.dat.book_management.repositories.RoleRepository;
import com.dat.book_management.repositories.UserRepository;
import com.dat.book_management.roles.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MainController {
    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String page(){
        return "index.html";
    }

    @RequestMapping(value = "/logining", method = RequestMethod.POST)
    public User logining(@RequestBody Login login){

        authenticationController.login(login);

        return userRepository.findByEmail(login.getEmail());
    }

//    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public User signup(@RequestBody User user){
        for(User user1 : userRepository.findAll()){
            if(user1.getEmail().equals(user.getEmail())){
                return null;
            }
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        user.setRole(roleRepository.findByName("ROLE_MEMBER"));

        user = userRepository.save(user);

        StringBuilder builder = new StringBuilder();

        for(int i=0; i<6; i++){
            builder.append(new Random().nextInt(10));
        }

        String code = builder.toString();

        user.setCode(code);

        sendEmail(user.getEmail(), code);

        return user;
    }


    public void sendEmail(@RequestParam String email, String code) {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        //message.setTo(email);
        message.setTo(email);
        message.setSubject("[Mã xác nhận]");
        message.setText("Mã xác nhận của bạn là: " + code);

        // Send Message!
        mailSender.send(message);
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public User home(){
        return userRepository.findByEmail("ntdat@gmail.com");
    }

//    @RequestMapping(value = "/confirmCode", method = RequestMethod.POST)
//    public User confirmCode(@RequestBody String code, User user){
//        return new User();
//    }
}
