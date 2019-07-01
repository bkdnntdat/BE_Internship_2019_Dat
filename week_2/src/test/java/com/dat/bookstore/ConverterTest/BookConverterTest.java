package com.dat.bookstore.ConverterTest;

import com.dat.bookstore.converters.bases.Converter;
import com.dat.bookstore.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

@Component
class SampleConverter extends Converter<String, Integer>{
    @Override
    public Integer convert(String source) throws BadRequestException{
        return Integer.parseInt(source);
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class BookConverterTest {
    @Autowired
    private SampleConverter sampleConverter;

    @Test
    public void test_convert_List(){
        String[] numbers = new String[]{"1","2","3"};

        List<Integer> result = sampleConverter.convert(Arrays.asList(numbers));

        Assert.assertEquals(result, Arrays.asList(1,2,3));
    }
}
