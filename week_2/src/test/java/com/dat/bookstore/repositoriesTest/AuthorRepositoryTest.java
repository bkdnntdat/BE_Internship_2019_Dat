package com.dat.bookstore.repositoriesTest;

import com.dat.bookstore.models.Author;
import com.dat.bookstore.repositories.AuthorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void test_findByName() {
        Author author= new Author();
        author.setName("Name");
        author.setCountry("Conuntry");
        author.setBirthday(new Date(1999,3,21));
        authorRepository.save(author);

        Assert.assertNotNull(authorRepository.findByName("Name"));
        Assert.assertEquals(authorRepository.findByName("Nam").toString(),"[]");
    }
}
