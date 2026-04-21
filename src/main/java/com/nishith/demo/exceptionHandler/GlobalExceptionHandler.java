package com.nishith.demo.exceptionHandler;

import com.nishith.demo.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorMessage> movieNotFoundException(MovieNotFoundException exception){
    ErrorMessage error=new ErrorMessage(HttpStatus.NOT_FOUND.value(),exception.getMessage(),System.currentTimeMillis());
    return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
}
@ExceptionHandler(EmptyListException.class)
    public ResponseEntity<ErrorMessage> emptyList(EmptyListException exception){
    ErrorMessage error=new ErrorMessage(HttpStatus.NO_CONTENT.value(),exception.getMessage(),System.currentTimeMillis());
    return new ResponseEntity<ErrorMessage>(error,HttpStatus.NO_CONTENT);
}

}
