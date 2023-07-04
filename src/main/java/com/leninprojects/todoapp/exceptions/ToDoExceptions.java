package com.leninprojects.todoapp.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

@Data
public class ToDoExceptions extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ToDoExceptions(String message, HttpStatus httpStatus) {
        super(message);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMessage = new HashMap<>();
        jsonMessage.put("code", httpStatus.value());
        jsonMessage.put("message", message);
        try {
            this.message = mapper.writeValueAsString(jsonMessage);
        } catch (JsonProcessingException e) {
            this.message = message;
        }
        this.httpStatus = httpStatus;
    }
}
