package com.lcwd.restaurant.exceptions;

import com.lcwd.restaurant.dtos.CustomMessage;
import org.hibernate.ResourceClosedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceClosedException.class)
    public ResponseEntity<CustomMessage> handleResource(ResourceNotFoundException e){
        CustomMessage message = new CustomMessage();
        message.setSuccess(false) ;
        message.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<CustomMessage>> handleResource(MethodArgumentNotValidException e) {
//        e.getBindingResult().getAllErrors().stream().map()
        return null;
    }
}
