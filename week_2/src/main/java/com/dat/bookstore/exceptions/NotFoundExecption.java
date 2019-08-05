package com.dat.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundExecption extends RuntimeException {
    public NotFoundExecption(String reason) {
        super(reason);
    }
}
