package com.Dat.BookStore.repositories;

import com.Dat.BookStore.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Iterable<Author> findByName(String name);
}
