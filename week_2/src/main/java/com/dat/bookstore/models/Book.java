package com.dat.bookstore.models;

import lombok.Data;
import lombok.NonNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    @NotBlank(message = "Name is mandatory")
    private String name;

    @ManyToOne(cascade=CascadeType.ALL)
    private Author author;

    @ManyToOne(cascade=CascadeType.ALL)
    private Category category;

    @Min(value = 1800, message = "Invalid year")
    @Max(value = 2019, message = "Invalid year")
    private int year;

    public Book() {

    }
}