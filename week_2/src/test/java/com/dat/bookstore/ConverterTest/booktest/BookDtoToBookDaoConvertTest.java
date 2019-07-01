package com.dat.bookstore.ConverterTest.booktest;

import com.dat.bookstore.converters.bases.Converter;
import com.dat.bookstore.dto.BookDTO;
import com.dat.bookstore.models.Author;
import com.dat.bookstore.models.Book;
import com.dat.bookstore.models.Category;
import com.dat.bookstore.repositories.AuthorRepository;
import com.dat.bookstore.repositories.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BookDtoToBookDaoConvertTest {
    @Autowired
    private Converter<BookDTO, Book> bookDtoBookDaoConverter;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Author author = new Author();

    private Category category = new Category();

    @Before
    public void init(){
        author.setName("Author name");
        author.setBirthday(new Date(1999,21,3));
        author.setCountry("Việt Nam");

        author = authorRepository.save(new Author());

        category.setTheLoai("Thể loại");

        category = categoryRepository.save(category);
    }

    @Test
    public void test_convert(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("Book");
        bookDTO.setYear(1999);
        bookDTO.setId(12);
        bookDTO.setAuthorId(author.getId());
        bookDTO.setCategotyId(category.getId());

        Book book = bookDtoBookDaoConverter.convert(bookDTO);
        Assert.assertEquals(book.getId(), 12);
        Assert.assertEquals(book.getName(), "Book");
        Assert.assertEquals(book.getYear(), 1999);
        Assert.assertEquals(book.getAuthor().getId(),author.getId());
        Assert.assertEquals(book.getCategory().getId(),category.getId());
    }
}
