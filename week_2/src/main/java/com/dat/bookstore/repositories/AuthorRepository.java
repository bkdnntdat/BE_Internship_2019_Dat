package com.dat.bookstore.repositories;

import com.dat.bookstore.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Iterable<Author> findByName(String name);
}
