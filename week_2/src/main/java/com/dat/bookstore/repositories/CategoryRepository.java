package com.dat.bookstore.repositories;

import com.dat.bookstore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    Iterable<Category> findByTheLoaiContaining(String theLoai);
}
