package com.dat.book_management.controllers;

import com.dat.book_management.models.Author;
import com.dat.book_management.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping(value = "/")
    public String getBooks(){
        return "listBooks";
    }

//    @PostMapping(value = "/post")
//    public String postBook(String title, Author author, String description){
//
//    }
}
