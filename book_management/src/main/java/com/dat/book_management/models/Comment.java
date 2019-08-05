package com.dat.book_management.models;


import com.dat.book_management.roles.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String message;

    private Date time;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    @JsonAlias("created_at")
    private Date createdAt;

    @JsonAlias("updated_at")
    private Date updatedAt;

    public Comment(){}
}
