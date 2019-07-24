package com.dat.blog.controllers;

import com.dat.blog.exceptions.NotFoundException;
import com.dat.blog.models.Post;
import com.dat.blog.models.Tag;
import com.dat.blog.repositories.CommentRepository;
import com.dat.blog.repositories.PostRepository;
import com.dat.blog.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    List<Post> get() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    Post get(@PathVariable int id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isPresent()) return post.get();

        throw new NotFoundException(String.format("Post id %d not found", id));
    }

    @GetMapping("/find")
    List<Post> get(@RequestParam String post) {
        return postRepository.findByContentContaining(post);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        if (!postRepository.existsById(id)) throw new NotFoundException(String.format("Post id %d not found", id));

        postRepository.deleteById(id);
    }

    @PostMapping
    void post(@RequestBody Post post) {
        List<Tag> tags = post.getTags();

        List<Tag> tempTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (!tagRepository.existsById(tag.getId())) {
                Optional<Tag> tag1 = tagRepository.findByName(tag.getName());
                if (tag1.isPresent()) {
                    tag = tag1.get();
                } else {
                    tag.setId(0);
                    tag = tagRepository.save(tag);
                }
            }
            tempTags.add(tag);
        }

        post.setTags(tempTags);

        post.setId(0);
        postRepository.save(post);
    }

    @PutMapping
    void put(@RequestBody Post post) {
        List<Tag> tags = post.getTags();

        List<Tag> tempTags = new ArrayList<>();
        for (Tag tag : tags) {
            if (!tagRepository.existsById(tag.getId())) {
                Optional<Tag> tag1 = tagRepository.findByName(tag.getName());
                if (tag1.isPresent()) {
                    tag = tag1.get();
                } else {
                    tag.setId(0);
                    tag = tagRepository.save(tag);
                }
            }
            tempTags.add(tag);
        }

        post.setTags(tempTags);


//        List<Comment> comments = post.getComments();
//
//        List<Comment> tempComments = new ArrayList<>();
//
//        for (Comment comment : comments) {
//            if (!commentRepository.existsById(comment.getId())) {
//                Optional<Comment> comment1 = commentRepository.findByComment(comment.getComment());
//                if (comment1.isPresent()){
//                    comment=comment1.get();
//                }
//                else {
//                    comment.setId(0);
//                    comment = commentRepository.save(comment);
//                }
//            }
//            tempComments.add(comment);
//        }
//
//        post.setComments(tempComments);

        postRepository.save(post);
    }
}
