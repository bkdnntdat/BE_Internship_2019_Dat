package com.Dat.BookStore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundExecption extends RuntimeException {
    public NotFoundExecption(String reason) {
        super(reason);
    }
}
