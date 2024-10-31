package com.hawkingbros.adapter.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MsgException.class)
    public ResponseEntity<Object> handleException(MsgException ex, WebRequest request) {
        System.out.println(ex.getMessage());
        return handleExceptionInternal(ex, null, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }
}
