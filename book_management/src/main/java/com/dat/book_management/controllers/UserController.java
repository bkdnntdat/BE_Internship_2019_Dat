package com.dat.book_management.controllers;

import com.dat.book_management.configurations.TokenProvider;
import com.dat.book_management.models.Login;
import com.dat.book_management.repositories.RoleRepository;
import com.dat.book_management.repositories.UserRepository;
import com.dat.book_management.roles.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private MailSender mailSender;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody User user){
        if(userRepository.findByEmail(user.getEmail()) !=null){
            return null;
        }
        user.setRole(roleRepository.findByName("ROLE_MEMBER"));

        Login login = new Login();

        login.setEmail(user.getEmail());
        login.setPassword(user.getPassword());

//        StringBuilder builder = new StringBuilder();
//
//        for(int i=0; i<6; i++){
//            builder.append(new Random().nextInt(10));
//        }

//        user.setCode(builder.toString());

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        user = userRepository.save(user);

//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(user.getEmail());
//        message.setSubject("[Mã xác nhận]");
//        message.setText("Mã xác nhận của bạn là: " + user.getCode());
//
//        mailSender.send(message);

        return authenticationController.login(login);
    }

//    @PostMapping("/confirm")
//    public ResponseEntity<?> confirm(@RequestBody CodeToken codeToken){
//        final String token = codeToken.token;
//        String email = tokenProvider.getUsernameFromToken(codeToken.token).toLowerCase();
//        User user = userRepository.findByEmail(email);
//
//        if(user.getCode().equals(codeToken.code)){
//            user.setCode(null);
//            userRepository.save(user);
//            return ResponseEntity.ok(new AuthToken(codeToken.token));
//        }
//        return null;
//    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id){
        return userRepository.findById(id).get();
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user")
    public User getUserByToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }
}

//class CodeToken{
//    String code;
//    String token;
//}