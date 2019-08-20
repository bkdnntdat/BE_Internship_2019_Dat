package com.dat.book_management.controllers;

import com.dat.book_management.models.Author;
import com.dat.book_management.models.Book;
import com.dat.book_management.models.Comment;
import com.dat.book_management.repositories.BookRepository;
import com.dat.book_management.repositories.CommentRepository;
import com.dat.book_management.roles.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Book> getBooksEnabled(@RequestParam Boolean enabled){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Book> bookList = bookRepository.findByEnabled(enabled);
        return bookList;
    }

    @PostMapping
    public Book postBook(@Valid @RequestBody Book book){
        book.setCreatedAt(new Date());
        bookRepository.save(book);
        return book;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id){
        Book book = bookRepository.findById(id).get();
        return book;
    }

    @GetMapping("/user")
    public List<Book> getMyBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return bookRepository.findByUserEmail(currentPrincipalName);
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

    @DeleteMapping
    public void deteleBook(@RequestParam int id){
        bookRepository.deleteById(id);
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam int id){
        return commentRepository.findByBookId(id);
    }

    @PostMapping("/comments")
    public Comment postComment(@RequestBody Comment comment){
        comment.setTime(new Date());
        return this.commentRepository.save(comment);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String key){
        List<Book> booksByTitle = bookRepository.findByTitleContaining(key);
        List<Book> booksByAuthor = bookRepository.findByAuthorContaining(key);
        List<Book> result = new ArrayList<>();
        result.addAll(booksByTitle);
        for(Book book : booksByAuthor){
            Boolean add = true;
            for(Book book1 : booksByTitle) {
                if(book == book1){
                    add = false;
                    break;
                }
            }
            if(add) result.add(book);
        }

        return result;
    }
}
