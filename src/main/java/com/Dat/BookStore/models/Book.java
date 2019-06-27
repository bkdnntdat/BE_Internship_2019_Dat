package com.Dat.BookStore.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Category category;

    private int year;
}