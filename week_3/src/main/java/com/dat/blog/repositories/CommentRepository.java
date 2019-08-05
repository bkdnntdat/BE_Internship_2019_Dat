package com.dat.blog.repositories;

import com.dat.blog.models.Comment;
import com.dat.blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByContentContaining(String comment);
    Optional<Comment> findByContent(String comment);
    List<Comment> findByUserId(int userId);
    void deleteByUserId(int userId);
}