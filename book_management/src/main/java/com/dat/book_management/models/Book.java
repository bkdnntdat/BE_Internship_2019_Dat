package com.dat.book_management.models;

import com.dat.book_management.roles.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

//    @ManyToOne
    private String author;

    private String description;

    @JsonAlias("created_at")
    private Date createdAt;

    @JsonAlias("updated_at")
    private Date updatedAt;

    private String image;

    private boolean enabled;

    private int year;

    @ManyToOne
    private User user;
}
