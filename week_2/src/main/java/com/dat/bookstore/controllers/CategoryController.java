package com.dat.bookstore.controllers;

import com.dat.bookstore.exceptions.NotFoundExecption;
import com.dat.bookstore.models.Category;
import com.dat.bookstore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) return optionalCategory;

        throw new NotFoundExecption(String.format("Category id %d not found", id));
    }

    @GetMapping("/find")
    Iterable<Category> get(@RequestParam String theLoai) {
        return categoryRepository.findByTheLoaiContaining(theLoai);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    void post(@RequestBody Category category) {
        category.setId(0);
        categoryRepository.save(category);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (!categoryRepository.existsById(id))
            throw new NotFoundExecption(String.format("Category id %d not found", id));
        categoryRepository.deleteById(id);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping()
    void put(@RequestBody Category category) {
        categoryRepository.save(category);
    }
}
