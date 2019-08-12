package com.dat.book_management.repositories;

import com.dat.book_management.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByTitle(String title);
    List<Book> findByTitleContaining(String title);
    List<Book> findByEnabled(boolean enabled);

}
