package com.Dat.BookStore.controllers;

import com.Dat.BookStore.models.Author;
import com.Dat.BookStore.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    Iterable<Author> get() {
        return authorRepository.findAll();
    }

    @GetMapping("/find")
    Iterable<Author> get(@RequestParam String name) {
        return authorRepository.findByName(name);
    }

    @GetMapping("/{id}")
    Optional<Author> get(@PathVariable int id) {
        return authorRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        authorRepository.deleteById(id);
    }

    @PostMapping
    void post(@RequestBody Author author) {
        author.setId(0);
        authorRepository.save(author);
    }

    @PutMapping
    void put(@RequestBody Author author) {
        authorRepository.save(author);
    }
}
