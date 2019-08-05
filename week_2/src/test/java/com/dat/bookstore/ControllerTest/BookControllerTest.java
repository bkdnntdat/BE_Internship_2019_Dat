package com.dat.bookstore.ControllerTest;

import com.dat.bookstore.dao.Login;
import com.dat.bookstore.repositories.BookRepository;
import com.dat.bookstore.models.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BookControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    static Book book1 = new Book();
    static Book book2 = new Book();

    @Before
    @WithMockUser(username = "admin")
    public void login() throws Exception {
        Login bookPost = new Login();
        bookPost.setUsername("admin");
        bookPost.setPassword("1234");

        String json = gson.toJson(bookPost);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        book1 = bookRepository.save(loadFromFile("init.json"));
        book2 = bookRepository.save(loadFromFile("init1.json"));
    }

    @After
    @WithMockUser(username = "admin")
    public void reset() {
        bookRepository.deleteAll();
    }

    public Book loadFromFile(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder json = new StringBuilder();
        String read;
        while ((read = reader.readLine()) != null) json.append(read);

        return gson.fromJson(json.toString(), Book.class);
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_getAllBook() throws Exception {

        String json = mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Book> books = gson.fromJson(json, new TypeToken<List<Book>>(){}.getType());

        Assert.assertArrayEquals(books.toArray(new Book[0]), new Book[]{book1, book2});
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_getBook_Found() throws Exception {
        mockMvc.perform(get("/api/books/" + book1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.equalTo(book1.getId())))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Tôi thấy hoa vàng trên cỏ xanh")));
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_getBook_NotFound() throws Exception {
        mockMvc.perform(get("/api/books/" + 90))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_deleteBook_NotFound() throws Exception {
        mockMvc.perform(delete("/api/books/" + 5))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_deleteBook_Found() throws Exception {
        mockMvc.perform(delete("/api/books/" + book1.getId()))
                .andExpect(status().isOk());

        Assert.assertFalse(bookRepository.findById(10).isPresent());
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_put_Found() throws Exception{

        Book book = loadFromFile("init2.json");

        book.setId(book2.getId());

        String json = gson.toJson(book);

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        Optional<Book> b = bookRepository.findById(book2.getId());

        Assert.assertTrue(b.isPresent());
        Assert.assertEquals(b.get().getName(), "Cô gái đến từ hôm qua");
    }

    @WithMockUser(username = "admin")
    @Test
    public void test_post_ok() throws Exception{
        String json = gson.toJson(loadFromFile("init2.json"));

        mockMvc.perform(put("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());


        ArrayList<Book> books = (ArrayList<Book>) bookRepository.findAll();
        Book book = books.get(books.size()-1);

        Assert.assertEquals(book.getName(), "Cô gái đến từ hôm qua");
    }
}