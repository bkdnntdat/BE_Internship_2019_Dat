package com.Dat.BookStore.repositories;

import com.Dat.BookStore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Iterable<Category> findByTheLoaiContaining(String theLoai);
}
