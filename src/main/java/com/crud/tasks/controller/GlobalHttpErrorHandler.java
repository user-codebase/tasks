package com.crud.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.DefaultResponseErrorHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends DefaultResponseErrorHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException e) {
        return new ResponseEntity<>("Task with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
