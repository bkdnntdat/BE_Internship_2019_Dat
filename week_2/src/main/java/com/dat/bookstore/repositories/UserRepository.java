package com.dat.bookstore.repositories;

import com.dat.bookstore.role.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
