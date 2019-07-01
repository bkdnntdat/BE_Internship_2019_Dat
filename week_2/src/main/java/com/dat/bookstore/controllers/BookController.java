package com.dat.bookstore.controllers;

import com.dat.bookstore.converters.bases.Converter;
import com.dat.bookstore.dto.BookDTO;
import com.dat.bookstore.exceptions.NotFoundExecption;
import com.dat.bookstore.models.Author;
import com.dat.bookstore.models.Book;
import com.dat.bookstore.models.Category;
import com.dat.bookstore.repositories.AuthorRepository;
import com.dat.bookstore.repositories.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import com.dat.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private Converter<BookDTO, Book> bookDtoBookDaoConverter;

    @Autowired
    private Converter<Book, BookDTO> bookDaoBookDtoConverter;

    @GetMapping("/{id}")
    public BookDTO get(@PathVariable int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) return bookDaoBookDtoConverter.convert(optionalBook.get());

        throw new NotFoundExecption(String.format("book id %d not found", id));
    }

    @GetMapping
    List<BookDTO> get() {
        return bookDaoBookDtoConverter.convert(bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    @GetMapping("/find")
    Iterable<Book> get(@RequestParam String request) {
        return bookRepository.findByNameContaining(request);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (!bookRepository.existsById(id)) throw new NotFoundExecption(String.format("book id %d not found", id));
        bookRepository.deleteById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    void post(@Valid @RequestBody Book book) {
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

    @Secured("ROLE_ADMIN")
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