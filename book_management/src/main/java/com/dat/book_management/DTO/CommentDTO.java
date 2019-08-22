package com.dat.book_management.DTO;

import com.dat.book_management.models.Book;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDTO {
    @NotBlank
    private String message;

    @NotNull
    private Book book;
}
