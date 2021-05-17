package com.Minet.Minet.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class ExceptionResponse {

    private String message;

    private String trace;

    private HttpStatus httpStatus;
}
