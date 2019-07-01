package com.dat.bookstore.repositoriesTest;

import com.dat.bookstore.repositories.RoleRepository;
import com.dat.bookstore.role.Role;
import com.dat.bookstore.role.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void test_findByUsername(){
        roleRepository.save(new Role("abc", "asd"));

        Assert.assertNotNull(roleRepository.findByName("abc"));
        Assert.assertNull(roleRepository.findByName("ab"));
    }
}
