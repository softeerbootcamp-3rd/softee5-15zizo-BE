package com.zizo.carteeng.common;

import com.zizo.carteeng.common.errors.ErrorCode;
import com.zizo.carteeng.common.errors.ErrorException;
import com.zizo.carteeng.common.errors.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(value = { ErrorException.class })
    protected ResponseEntity<ErrorResponse> ErrorException(ErrorException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}