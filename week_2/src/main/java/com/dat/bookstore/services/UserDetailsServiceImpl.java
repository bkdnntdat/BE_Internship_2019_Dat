package com.dat.bookstore.services;

import com.dat.bookstore.repositories.UserRepository;
import com.dat.bookstore.role.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dat.bookstore.role.Role;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);

        if(user==null) throw new UsernameNotFoundException("User not found");

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        Set<Role> roles = user.getRoles();

        for(Role role : roles){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthoritySet);
    }
}
