package com.Minet.Minet.exception;

import com.Minet.Minet.controller.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FileStorageException.class)
    public @ResponseBody ExceptionResponse fileStorageException(FileStorageException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage("해당 경로에 파일이 중복됨");
        return exceptionResponse;
    }
}
