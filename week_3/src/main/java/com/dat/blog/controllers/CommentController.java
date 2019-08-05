package com.dat.blog.controllers;

import com.dat.blog.exceptions.NotFoundException;
import com.dat.blog.models.Comment;
import com.dat.blog.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/controllers")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    List<Comment> get() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    Comment get(@PathVariable int id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) return comment.get();

        throw new NotFoundException(String.format("Comment id %d not found", id));
    }

    @GetMapping("/find")
    List<Comment> get(@RequestParam String comment){
        return commentRepository.findByContentContaining(comment);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id){
        if (!commentRepository.existsById(id)) throw new NotFoundException(String.format("Comment id %d not found", id));
        commentRepository.deleteById(id);
    }

    @PostMapping
    void post(@RequestBody Comment comment){
        comment.setId(0);
        commentRepository.save(comment);
    }

    @PutMapping
    void put(@RequestBody Comment comment){
        if(!commentRepository.existsById(comment.getId())) throw new NotFoundException(String.format("Comment id %d not found", comment.getId()));
        commentRepository.save(comment);
    }
}
