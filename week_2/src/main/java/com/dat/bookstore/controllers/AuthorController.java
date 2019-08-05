package com.dat.bookstore.controllers;

import com.dat.bookstore.exceptions.NotFoundExecption;
import com.dat.bookstore.models.Author;
import com.dat.bookstore.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) return optionalAuthor;

        throw new NotFoundExecption(String.format("Author id %d not found", id));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (!authorRepository.existsById(id)) throw new NotFoundExecption(String.format("Author id %d not found", id));
        authorRepository.deleteById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    void post(@RequestBody Author author) {
        author.setId(0);
        authorRepository.save(author);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping
    void put(@RequestBody Author author) {
        authorRepository.save(author);
    }
}
