package com.Dat.BookStore.controllers;

import com.Dat.BookStore.models.Category;
import com.Dat.BookStore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping()
    Iterable<Category> get() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Category> get(@PathVariable int id) {
        return categoryRepository.findById(id);
    }

    @GetMapping("/find")
    Iterable<Category> get(@RequestParam String theLoai){
        return categoryRepository.findByTheLoaiContaining(theLoai);
    }

    @PostMapping()
    void post(@RequestBody Category category) {
        category.setId(0);
        categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        categoryRepository.deleteById(id);
    }

    @PutMapping()
    void put(@RequestBody Category category) {
        categoryRepository.save(category);
    }
}
