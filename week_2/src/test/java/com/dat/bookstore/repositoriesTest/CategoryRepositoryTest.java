package com.dat.bookstore.repositoriesTest;

import com.dat.bookstore.models.Category;
import com.dat.bookstore.repositories.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test_findByTheLoaiContaining(){
        Category category = new Category();
        category.setTheLoai("Truyện ma");

        categoryRepository.save(category);

        Assert.assertNotNull(categoryRepository.findByTheLoaiContaining("uy"));
        Assert.assertEquals(categoryRepository.findByTheLoaiContaining("c").toString(),"[]");
    }

}
