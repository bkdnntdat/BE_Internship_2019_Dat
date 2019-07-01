package com.dat.bookstore.repositories;

import com.dat.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer>{
    Iterable<Book> findByNameContaining(String nameBook);
}
