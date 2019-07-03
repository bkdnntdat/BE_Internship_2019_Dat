package com.dat.blog;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    @NotBlank(message = "Bạn đang nghĩ gì")
    private String post;

    @OneToMany(cascade = CascadeType.ALL)
    Tag tag;

    @OneToMany(cascade = CascadeType.ALL)
    Comment comments;
}
