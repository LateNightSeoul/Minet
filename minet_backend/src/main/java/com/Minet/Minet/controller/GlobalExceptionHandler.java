package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.ExceptionResponse;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileStorageException.class)
    public ExceptionResponse fileStorageException(FileStorageException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse exception(Exception e) {
        e.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }
}
