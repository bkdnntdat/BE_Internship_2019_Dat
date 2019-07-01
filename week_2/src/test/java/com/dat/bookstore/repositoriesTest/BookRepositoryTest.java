package com.dat.bookstore.repositoriesTest;

import com.dat.bookstore.models.Author;
import com.dat.bookstore.models.Category;
import com.dat.bookstore.repositories.BookRepository;
import com.dat.bookstore.models.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    public void test_findByNameContaining(){
        Category category = new Category();
        category.setTheLoai("Truyện ma");

        Author author= new Author();
        author.setName("Name");
        author.setCountry("Conuntry");
        author.setBirthday(new Date(1999,3,21));

        Book book = new Book();
        book.setName("Name");
        book.setAuthor(author);
        book.setCategory(category);
        book.setYear(1999);

        bookRepository.save(book);

        Assert.assertNotNull(bookRepository.findByNameContaining("N"));
        Assert.assertNotNull(bookRepository.findByNameContaining("am"));
        Assert.assertEquals(bookRepository.findByNameContaining("xasd").toString(),"[]");
    }
}
