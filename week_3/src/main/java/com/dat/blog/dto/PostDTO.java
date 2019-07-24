package com.dat.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PostDTO {
    int id;

    @NotBlank(message = "Title is not blank")
    String title;

    @NotBlank(message = "Content is not blank")
    String content;

    List<Integer> tagsId;

    List<Integer> commentsId;

    int userId;
}
