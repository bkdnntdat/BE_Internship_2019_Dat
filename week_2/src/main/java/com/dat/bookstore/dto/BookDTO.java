package com.dat.bookstore.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class BookDTO {
    int id;

    @NotBlank(message = "Name is madatory")
    String name;

    int authorId;

    int categotyId;

    @Min(value = 1800, message = "Invalid year")
    @Max(value = 2019, message = "Invalid year")
    int year;
}
