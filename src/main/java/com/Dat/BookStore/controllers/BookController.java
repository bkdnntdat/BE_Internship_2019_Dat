package com.Dat.BookStore.controllers;

import com.Dat.BookStore.exceptions.NotFoundExecption;
import com.Dat.BookStore.models.Author;
import com.Dat.BookStore.models.Book;
import com.Dat.BookStore.models.Category;
import com.Dat.BookStore.repositories.AuthorRepository;
import com.Dat.BookStore.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;
import com.Dat.BookStore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@RestController
@RequestMapping("/api/books")
class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}")
    Book get(@PathVariable int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) return optionalBook.get();

        throw new NotFoundExecption(String.format("book id %d not found", id));
    }

    @GetMapping
    Iterable<Book> get() {
        return bookRepository.findAll();
    }

    @GetMapping("/find")
    Iterable<Book> get(@RequestParam String request) {
        return bookRepository.findByNameContaining(request);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (!bookRepository.existsById(id)) throw new NotFoundExecption(String.format("Book id %d not found", id));
        bookRepository.deleteById(id);
    }

    @PostMapping()
    void post(@RequestBody Book book) {
        book.setId(0);

        Author author = book.getAuthor();
        if (author != null && !authorRepository.existsById(author.getId())) {
            author = authorRepository.save(author);
            book.setAuthor(author);
        }

        Category category = book.getCategory();
        if(category!= null && !categoryRepository.existsById(category.getId())){
            category = categoryRepository.save(category);
            book.setCategory(category);
        }

        bookRepository.save(book);
    }

    @PutMapping()
    void put(@RequestBody Book book) {
        Author author = book.getAuthor();
        if (author != null && !authorRepository.existsById(author.getId())) {
            author = authorRepository.save(author);
            book.setAuthor(author);
        }

        Category category = book.getCategory();
        if(category!= null && !categoryRepository.existsById(category.getId())){
            category = categoryRepository.save(category);
            book.setCategory(category);
        }

        bookRepository.save(book);
    }
}