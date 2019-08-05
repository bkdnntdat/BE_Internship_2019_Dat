package com.dat.bookstore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaSpringApplicationTests {
	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test_datasourceUrl(){
		Assert.assertEquals(datasourceUrl,"jdbc:mysql://localhost:3306/bookstore-test");
	}

}
