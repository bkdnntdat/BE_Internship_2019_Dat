package com.dat.book_management.service;

import com.dat.book_management.models.Comment;
import com.dat.book_management.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments(int id){
        return commentRepository.findByBookId(id);
    }

    public Comment postComment(Comment comment){
        comment.setTime(new Date());
        return this.commentRepository.save(comment);
    }
}
