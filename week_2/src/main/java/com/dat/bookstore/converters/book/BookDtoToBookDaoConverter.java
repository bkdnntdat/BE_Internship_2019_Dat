package com.dat.bookstore.converters.book;

import com.dat.bookstore.converters.bases.Converter;
import com.dat.bookstore.dto.BookDTO;
import com.dat.bookstore.exceptions.BadRequestException;
import com.dat.bookstore.models.Author;
import com.dat.bookstore.models.Category;
import com.dat.bookstore.repositories.AuthorRepository;
import com.dat.bookstore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dat.bookstore.models.Book;

import java.util.Optional;

@Component
public class BookDtoToBookDaoConverter extends Converter<BookDTO, Book> {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Book convert(BookDTO source){
        Book book = new Book();

        book.setId(source.getId());
        book.setName(source.getName());
        book.setYear(source.getYear());

        if(source.getAuthorId()>0){
            Optional<Author> author = authorRepository.findById(source.getAuthorId());

            if(author.isPresent()){
                book.setAuthor(author.get());
            }else{
                throw new BadRequestException("Invalid author id " + source.getAuthorId());
            }
        }

        if(source.getCategotyId()>0){
            Optional<Category> category = categoryRepository.findById(source.getCategotyId());

            if(category.isPresent()){
                book.setCategory(category.get());
            }else{
                throw new BadRequestException("Invalid category id " + source.getCategotyId());
            }
        }

        return book;
    }
}
