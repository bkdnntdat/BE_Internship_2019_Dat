package com.dat.book_management.controllers;

import com.dat.book_management.models.Author;
import com.dat.book_management.models.Book;
import com.dat.book_management.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    @PostMapping
    public Book postBook(@RequestBody Book book){
        book.setCreatedAt(new Date());
        book.setEnabled(true);
        bookRepository.save(book);
        return book;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id){
        return bookRepository.findById(id).get();
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book){
        book.setUpdatedAt(new Date());
        book = bookRepository.save(book);
        return book;
    }

    @PutMapping("/books")
    public void enableBook(@RequestBody List<Book> books){
        for(Book book : books){
            bookRepository.save(book);
        }
    }
}
