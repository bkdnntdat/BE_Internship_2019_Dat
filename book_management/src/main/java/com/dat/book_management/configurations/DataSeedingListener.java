package com.dat.book_management.configurations;


import com.dat.book_management.repositories.RoleRepository;
import com.dat.book_management.repositories.UserRepository;
import com.dat.book_management.roles.Role;
import com.dat.book_management.roles.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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

    public void addUserIfMissing(String email, String password, String firstName, String lastName, String avt, String role) {

        if (userRepository.findByEmail(email) == null) {
            User user = new User();

            user.setEmail(email);

            user.setPassword(new BCryptPasswordEncoder().encode(password));

            user.setFirstName(firstName);

            user.setLastName(lastName);

            user.setAvt(avt);

            user.setRole(roleRepository.findByName(role));

            userRepository.save(user);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (signingkey == null || signingkey.length() == 0) {
            String jws = Jwts.builder()
                    .setSubject("book")
                    .signWith(SignatureAlgorithm.HS256, "BookApi")
                    .compact();

            System.out.println("Use this jwt key:");
            System.out.println("jwt-key=" + jws);
        }
        addRoleIfMissing("ROLE_ADMIN");
        addRoleIfMissing("ROLE_MEMBER");

        addUserIfMissing("bkdn.ntdat@gmail.com", "123", "Nguyen", "Dat", "https://bitly.vn/6svj", "ROLE_MEMBER");
        addUserIfMissing("hoaitrinh459@gmail.com", "123", "La", "Trinh", "https://bitly.vn/6svq", "ROLE_ADMIN");
    }
}
