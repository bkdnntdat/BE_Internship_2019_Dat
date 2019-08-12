package com.dat.book_management.repositories;

import com.dat.book_management.roles.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
