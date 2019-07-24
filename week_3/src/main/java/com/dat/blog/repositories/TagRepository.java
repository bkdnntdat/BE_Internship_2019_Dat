package com.dat.blog.repositories;

import com.dat.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    List<Tag> findByNameContaining(String name);
    Optional<Tag> findByName(String name);
}
