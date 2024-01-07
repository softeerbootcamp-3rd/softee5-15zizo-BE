package com.zizo.carteeng.common;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(BindException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }
}
