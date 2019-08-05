package com.dat.bookstore.converters.book;

import com.dat.bookstore.converters.bases.Converter;
import com.dat.bookstore.dto.BookDTO;
import com.dat.bookstore.exceptions.BadRequestException;
import com.dat.bookstore.models.Book;
import org.springframework.stereotype.Component;

@Component
public class BookDaoToBookDtoConverter extends Converter<Book, BookDTO> {
    @Override
    public BookDTO convert(Book source) throws BadRequestException {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(source.getId());
        bookDTO.setName(source.getName());
        bookDTO.setYear(source.getYear());

        if (source.getAuthor() != null) {
            bookDTO.setAuthorId(source.getAuthor().getId());
        }

        if (source.getCategory() != null) {
            bookDTO.setCategotyId(source.getCategory().getId());
        }

        return bookDTO;
    }
}
