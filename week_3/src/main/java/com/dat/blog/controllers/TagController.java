package com.dat.blog.controllers;

import com.dat.blog.exceptions.NotFoundException;
import com.dat.blog.models.Tag;
import com.dat.blog.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/tags")
public class TagController {
    @Autowired
    TagRepository tagRepository;

    @GetMapping
    List<Tag> get() {
        return tagRepository.findAll();
    }

    @GetMapping("/{id}")
    Tag get(@PathVariable int id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isPresent()) return tag.get();

        throw new NotFoundException(String.format("Tag id %d not found", id));
    }

    @GetMapping("/find")
    List<Tag> get(@RequestParam String category) {
        return tagRepository.findByNameContaining(category);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (!tagRepository.existsById(id)) throw new NotFoundException(String.format("Tag id %d not found", id));
        tagRepository.deleteById(id);
    }

    @PostMapping
    void post(@RequestBody Tag tag) {
        tag.setId(0);
        tagRepository.save(tag);
    }

    @PutMapping
    void put(@RequestBody Tag tag) {
        if (!tagRepository.existsById(tag.getId()))
            throw new NotFoundException(String.format("Tag id %d not found", tag.getId()));
        tagRepository.save(tag);
    }
}
