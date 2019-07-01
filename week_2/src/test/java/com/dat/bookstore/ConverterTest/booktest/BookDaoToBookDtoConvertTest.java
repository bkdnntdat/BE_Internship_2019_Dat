package com.dat.bookstore.ConverterTest.booktest;

import com.dat.bookstore.converters.bases.Converter;
import com.dat.bookstore.models.Author;
import com.dat.bookstore.models.Book;
import com.dat.bookstore.dto.BookDTO;
import com.dat.bookstore.models.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BookDaoToBookDtoConvertTest {
    @Autowired
    private Converter<Book, BookDTO> bookDaoBookDtoConverter;

    @Test
    public void test_Convert(){
        Author author = new Author();
        author.setId(90);

        Category category = new Category();
        category.setId(91);

        Book book = new Book();
        book.setId(92);
        book.setName("asd");
        book.setYear(1999);
        book.setAuthor(author);
        book.setCategory(category);

        BookDTO bookDTO= bookDaoBookDtoConverter.convert(book);

        Assert.assertEquals(bookDTO.getId(),92);
        Assert.assertEquals(bookDTO.getName(),"asd");
        Assert.assertEquals(bookDTO.getYear(),1999);
        Assert.assertEquals(bookDTO.getAuthorId(),90);
        Assert.assertEquals(bookDTO.getCategotyId(),91);
    }
}
