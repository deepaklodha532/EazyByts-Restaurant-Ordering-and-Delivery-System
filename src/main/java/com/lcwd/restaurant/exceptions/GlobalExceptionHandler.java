package com.lcwd.restaurant.exceptions;

import com.lcwd.restaurant.dtos.CustomMessage;
import org.hibernate.ResourceClosedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceClosedException.class)
    public ResponseEntity<CustomMessage> handleResource(ResourceNotFoundException e){
        CustomMessage message = new CustomMessage();
        message.setSuccess(false) ;
        message.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }


    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<CustomMessage> badExceptionResource(BadApiRequestException e){
        CustomMessage message = new CustomMessage();
        message.setSuccess(false) ;
        message.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleResource(MethodArgumentNotValidException e) {
          List<ObjectError> allErrors=  e.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>() ;

        allErrors.stream().forEach(objectError -> {
            String message  = objectError.getDefaultMessage();
            String field =((FieldError) objectError).getField() ;
            response.put(field, message) ;
        });

        return new ResponseEntity<>(response ,HttpStatus.OK)  ;
    }
}
