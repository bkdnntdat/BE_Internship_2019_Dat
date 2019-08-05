package com.dat.blog.repositories;

import com.dat.blog.models.Post;
import com.dat.blog.models.Tag;
import com.dat.blog.role.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByContentContaining(String content);
    List<Post> findByTitleContaining(String title);
    List<Post> findByUser(User user);
    Post findByTitle(String title);
}
