package com.dat.blog.models;

import com.dat.blog.role.User;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String content;

    private String description;

    @ManyToMany
    private List<Tag> tags;

    @OneToMany
    private List<Comment> comments;

    private Date time;

    private Date timeUpdate;

    @ManyToOne
    private User user;

    public Post(){}
}
