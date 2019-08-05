package com.dat.blog.models;

import com.dat.blog.role.User;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    @NotBlank(message = "Nhập bình luận của bạn")
    private String content;

    private Date time;

    @ManyToOne
    private User user;

    public Comment(){}
}
