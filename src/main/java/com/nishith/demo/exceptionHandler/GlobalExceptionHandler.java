package com.nishith.demo.exceptionHandler;

import com.nishith.demo.model.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


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

@ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> expiredjwt(ExpiredJwtException exception){
      ErrorMessage error=new ErrorMessage(HttpStatus.UNAUTHORIZED.value(),"Jwt token is expired",System.currentTimeMillis());
      return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);

}
@ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorMessage> malformedJwt(MalformedJwtException exception){
           ErrorMessage error=new ErrorMessage(HttpStatus.UNAUTHORIZED.value(),"Jwt token is invalid",System.currentTimeMillis() );
           return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
}
@ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<ErrorMessage> signatureInvalid(io.jsonwebtoken.security.SignatureException exception){
    ErrorMessage error=new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Signature is invalid",System.currentTimeMillis());
    return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
}
@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException exception){
    ErrorMessage error =new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), "Invalid user Details",System.currentTimeMillis());
    return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
}
@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> AccessDenied(AccessDeniedException exception){
    ErrorMessage error= new ErrorMessage(HttpStatus.FORBIDDEN.value(), "Access Denied you do not have permission",System.currentTimeMillis());
    return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorMessage> methodValidEx(MethodArgumentNotValidException exception){
    String errors=exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(m->m.getField()+": "+ m.getDefaultMessage())
            .collect(Collectors.joining(", "));

    ErrorMessage error =new ErrorMessage(HttpStatus.BAD_REQUEST.value(),errors,System.currentTimeMillis());
    return new ResponseEntity<ErrorMessage>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalError(Exception e){
    ErrorMessage error =new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went Wrong",System.currentTimeMillis());
    return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
}
}
