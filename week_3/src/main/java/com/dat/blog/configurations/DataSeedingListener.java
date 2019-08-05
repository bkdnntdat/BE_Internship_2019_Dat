package com.dat.blog.configurations;

import com.dat.blog.repositories.PostRepository;
import com.dat.blog.repositories.RoleRepository;
import com.dat.blog.repositories.UserRepository;
import com.dat.blog.role.Role;
import com.dat.blog.role.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Configuration
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Value("${jwt-key}")
    private String signingkey;

    private void addRoleIfMissing(String name) {
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(new Role(name));
        }
    }

    public void addUserIfMissing(String username, String password, String firstName, String lastName, String email, String phoneNumber, String avt, String role) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User(username, new BCryptPasswordEncoder().encode(password), firstName, lastName, email, phoneNumber, avt);

            user.setRole(roleRepository.findByName(role));

            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (signingkey == null || signingkey.length() == 0) {
            String jws = Jwts.builder()
                    .setSubject("blog")
                    .signWith(SignatureAlgorithm.HS256, "BlogApi")
                    .compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }
        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_MEMBER");

        addUserIfMissing("user", "123", "Nguyen", "Dat", "bkdn.ntdat@gmail.com", "0393695360", "https://bitly.vn/6svj", "ROLE_MEMBER");
        addUserIfMissing("admin", "123", "La", "Trinh", "hoaitrinh459@gmail.com", "0708167512", "https://bitly.vn/6svq", "ROLE_ADMIN");
    }
}