package com.leninprojects.todoapp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ToDoExceptions extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ToDoExceptions(String message, HttpStatus httpStatus) {
        super(message);
        StringBuilder jsonMessage = new StringBuilder();
        jsonMessage.append("{");
        jsonMessage.append("\"code\": ").append(httpStatus.value()).append(",");
        jsonMessage.append("\"message\": \"").append(message).append("\"");
        jsonMessage.append("}");

        this.message = jsonMessage.toString();
        this.httpStatus = httpStatus;
    }
}
