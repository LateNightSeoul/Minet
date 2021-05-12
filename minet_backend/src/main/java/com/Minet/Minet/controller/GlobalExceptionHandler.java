package com.Minet.Minet.controller;

import com.Minet.Minet.controller.response.ExceptionResponse;
import com.Minet.Minet.exception.FileStorageException;
import com.Minet.Minet.service.FileService;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ExceptionResponse> fileStorage(FileStorageException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ExceptionResponse accessDenied(AccessDeniedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }

    @ExceptionHandler(JSONException.class)
    public ExceptionResponse json(JSONException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }

//    @ExceptionHandler(Exception.class)
//    public ExceptionResponse exception(Exception e) {
//        e.printStackTrace();
//        ExceptionResponse exceptionResponse = new ExceptionResponse();
//        exceptionResponse.setMessage(e.getMessage());
//        return exceptionResponse;
//    }
}
