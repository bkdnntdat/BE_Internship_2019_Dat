package com.dat.blog.repositories;

import com.dat.blog.role.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    void deleteByUsername(String username);
}
