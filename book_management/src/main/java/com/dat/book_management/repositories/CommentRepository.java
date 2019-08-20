package com.dat.book_management.repositories;

import com.dat.book_management.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBookId(int bookId);
}
