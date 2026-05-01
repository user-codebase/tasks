package com.crud.tasks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalHttpErrorHandlerTest {

    private final GlobalHttpErrorHandler handler = new GlobalHttpErrorHandler();

    @Test
    void shouldHandleTaskNotFoundException() {
        // Given
        TaskNotFoundException exception = new TaskNotFoundException();

        // When
        ResponseEntity<?> response =
                handler.handleTaskNotFoundException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Task with given id doesn't exist", response.getBody());
    }
}