package com.dat.bookstore.configurations;

import com.dat.bookstore.repositories.RoleRepository;
import com.dat.bookstore.repositories.UserRepository;
import com.dat.bookstore.role.Role;
import com.dat.bookstore.role.User;
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
    private String signingKey;

    private void addRoleIfMissing(String name, String description) {
        if (roleRepository.findByName(name) == null) {
            roleRepository.save(new Role(name, description));
        }
    }

    private void addUserIfMissing(String username, String firstName, String lastName, String password, String... roles) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User(username, firstName, lastName, new BCryptPasswordEncoder().encode(password));
            user.setRoles(new HashSet<>());

            for (String role : roles) {
                user.getRoles().add(roleRepository.findByName(role));
            }

            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(signingKey == null || signingKey.length() ==0){
            String jws = Jwts.builder()
                    .setSubject("bookstore")
                    .signWith(SignatureAlgorithm.HS256, "BookStoreApi").compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }
        addRoleIfMissing("ROLE_ADMIN", "Administrators");
        addRoleIfMissing("ROLE_MEMBER", "Members");

        addUserIfMissing("user", "Nguyen", "dat", "456", "ROLE_MEMBER");
        addUserIfMissing("admin", "La", "Trinh", "1234", "ROLE_MEMBER", "ROLE_ADMIN");
    }

}
