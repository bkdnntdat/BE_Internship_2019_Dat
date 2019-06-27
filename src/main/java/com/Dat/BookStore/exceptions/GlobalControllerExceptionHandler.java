package com.Dat.BookStore.exceptions;

import lombok.Data;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class ErrorModel{
    private String message;
    private String path;
}

@ControllerAdvice
@ConditionalOnProperty(prefix = "app", name = "disable-default-exception-handling")
public class GlobalControllerExceptionHandler {
    public ResponseEntity<ErrorModel> handleException(NotFoundExecption e, ServletWebRequest request){
        return new ResponseEntity<>(new ErrorModel(e.getMessage(),request.getRequest().getRequestURI()),HttpStatus.NOT_FOUND);
    }
}
