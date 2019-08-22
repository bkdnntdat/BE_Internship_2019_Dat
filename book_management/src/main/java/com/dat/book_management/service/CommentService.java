package com.dat.book_management.service;

import com.dat.book_management.DTO.CommentDTO;
import com.dat.book_management.models.Comment;
import com.dat.book_management.repositories.CommentRepository;
import com.dat.book_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> getComments(int id){
        List<Comment> comments = commentRepository.findByBookId(id);

        for(int i=0; i<comments.size(); i++){
            for(int j=i+1; j<comments.size(); j++){
                if(comments.get(i).getTime().before(comments.get(j).getTime())) {
                    Comment comment = comments.get(i);
                    comments.set(i, comments.get(j));
                    comments.set(j, comment);
                    j--;
                }
            }
        }
        return comments;
    }

    public Comment postComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setBook(commentDTO.getBook());
        comment.setMessage(commentDTO.getMessage());
        comment.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        comment.setTime(new Date());
        return this.commentRepository.save(comment);
    }
}
