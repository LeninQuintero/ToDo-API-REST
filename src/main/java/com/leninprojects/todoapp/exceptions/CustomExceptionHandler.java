package com.leninprojects.todoapp.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ToDoExceptions.class})
    protected ResponseEntity<Object> handleConflict(ToDoExceptions ex, WebRequest request) {
                String bodyOfResponse = ex.getMessage();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.asMediaType(MediaType.APPLICATION_JSON));
                return handleExceptionInternal(ex, bodyOfResponse, headers, ex.getHttpStatus(), request);
    }
}