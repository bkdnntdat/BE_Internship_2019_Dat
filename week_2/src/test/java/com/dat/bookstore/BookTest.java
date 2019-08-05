package com.dat.bookstore;

import com.dat.bookstore.dao.Login;
import com.dat.bookstore.repositories.BookRepository;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.Validator;

import com.dat.bookstore.models.Book;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static junit.framework.TestCase.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class BookTest {
    @Autowired
    private Validator validator;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        Gson gson = new Gson();
        Login bookPost = new Login();
        bookPost.setUsername("admin");
        bookPost.setPassword("1234");

        String json = gson.toJson(bookPost);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void test_book_OK() {
        Book book = new Book();
        book.setName("Hello");
        book.setYear(2010);
        assertTrue(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_year_under_1800() {
        Book book = new Book();
        book.setName("Hello");
        book.setYear(1799);
        assertFalse(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_year_above_2019() {
        Book book = new Book();
        book.setName("Hello");
        book.setYear(2101);
        assertFalse(validator.validate(book).isEmpty());
    }

    @Test
    public void test_book_invalid_name() {
        Book book = new Book();
        book.setName("");
        book.setYear(2000);
        assertFalse(validator.validate(book).isEmpty());
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_post_ok() throws Exception {

        Gson gson = new Gson();
        Book bookPost = new Book();
        bookPost.setYear(2001);
        bookPost.setName("Geometry");

        String json = gson.toJson(bookPost);

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        ArrayList<Book> books = (ArrayList<Book>) bookRepository.findAll();
        Book book = books.get(books.size() - 1);

        assertEquals(book.getName(), "Geometry");
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_post_not_ok() throws Exception{

        Gson gson = new Gson();
        Book bookPost = new Book();
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bookPost)))
                .andExpect(status().isBadRequest());

        bookPost.setName("Math");
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bookPost)))
                .andExpect(status().isBadRequest());

        bookPost.setYear(1799);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bookPost)))
                .andExpect(status().isBadRequest());

        bookPost.setYear(2101);
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(bookPost)))
                .andExpect(status().isBadRequest());
    }
}
