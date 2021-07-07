package com.Minet.Minet.controller.handler;

import com.Minet.Minet.controller.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.Minet.Minet.controller.response.ApiResponse.ERROR;

@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ResponseEntity<ApiResponse<?>> newResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(throwable, status), headers, status);
    }

    @ExceptionHandler(IllegalAccessException.class)
    private ResponseEntity<ApiResponse<?>> handleServiceRuntimeException(IllegalAccessException exception) {
        log.error(exception.getMessage());
        return newResponse(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
