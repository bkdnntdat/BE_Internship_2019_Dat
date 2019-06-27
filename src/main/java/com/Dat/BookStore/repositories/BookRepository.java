package com.Dat.BookStore.repositories;

import com.Dat.BookStore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer>{
    Iterable<Book> findByNameContaining(String nameBook);
}
