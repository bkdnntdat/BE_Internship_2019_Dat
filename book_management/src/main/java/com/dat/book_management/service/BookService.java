package com.dat.book_management.service;

import com.dat.book_management.DTO.BookDTO;
import com.dat.book_management.models.Book;
import com.dat.book_management.repositories.BookRepository;
import com.dat.book_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getBooksEnabled(Boolean enabled){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Book> bookList = bookRepository.findByEnabled(enabled);
        return bookList;
    }

    public Book postBook(BookDTO bookDTO){
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());
        book.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        bookRepository.save(book);
        return book;
    }

    public Book getBook(int id){
        Book book = bookRepository.findById(id).get();
        return book;
    }

    public List<Book> getMyBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return bookRepository.findByUserEmail(currentPrincipalName);
    }

    public Book updateBook(BookDTO bookDTO){
        Book book = bookRepository.findById(bookDTO.getId()).get();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setAuthor(bookDTO.getAuthor());
        book.setYear(bookDTO.getYear());
        book.setUpdatedAt(new Date());
        book = bookRepository.save(book);
        return book;
    }

    public List<Book> enableBook(int id) {
        Book book = bookRepository.findById(id).get();
        book.setEnabled(true);
        book = bookRepository.save(book);
        return getBooksEnabled(false);
    }

    public List<Book> disableBook(int id){
        Book book = bookRepository.findById(id).get();
        book.setEnabled(false);
        book = bookRepository.save(book);
        return getBooksEnabled(true);
    }

    public void sortByAuthor(List<Book> books) {
        for (int i = 0; i < books.size(); i++)
            for (int j = i + 1; j < books.size(); j++) {
                if (books.get(i).getAuthor().compareTo(books.get(j).getAuthor()) > 0) {
                    Book book = books.get(i);
                    books.set(i, books.get(j));
                    books.set(j, book);
                    j--;
                }
            }
    }

    public void sortByTitle(List<Book> books){
        for(int i=0; i<books.size(); i++)
            for(int j=i+1; j<books.size(); j++){
                if(books.get(i).getTitle().compareTo(books.get(j).getTitle())>0){
                    Book book = books.get(i);
                    books.set(i,books.get(j));
                    books.set(j,book);
                    j--;
                }
            }
    }

    public void sortByYear(List<Book> books){
        for(int i=0; i<books.size(); i++)
            for(int j=i+1; j<books.size(); j++){
                if(books.get(i).getYear()>books.get(j).getYear()){
                    Book book = books.get(i);
                    books.set(i,books.get(j));
                    books.set(j,book);
                    j--;
                }
            }
    }

    public void deteleBook(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> search(String key){
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
