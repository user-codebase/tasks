package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    void shouldGetAllTasks() {
        // Given
        List<Task> tasks = List.of(new Task(1L, "test", "desc"));
        when(repository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = dbService.getAllTasks();

        // Then
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getTitle());
    }

    @Test
    void shouldSaveTask() {
        // Given
        Task task = new Task(1L, "test", "desc");
        when(repository.save(task)).thenReturn(task);

        // When
        Task result = dbService.saveTask(task);

        // Then
        assertNotNull(result);
        assertEquals("test", result.getTitle());
    }

    @Test
    void shouldGetTaskById() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "test", "desc");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTaskById(1L);

        // Then
        assertEquals("test", result.getTitle());
    }

    @Test
    void shouldGetTaskWhenExists() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "test", "desc");
        when(repository.findById(1L)).thenReturn(Optional.of(task));

        // When
        Task result = dbService.getTask(1L);

        // Then
        assertEquals("test", result.getTitle());
    }

    @Test
    void shouldDeleteTask() {
        // When
        dbService.deleteTask(1L);

        // Then
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldCallSaveRepository() {
        Task task = new Task(1L, "test", "desc");

        dbService.saveTask(task);

        verify(repository).save(task);
    }


    @Test
    void shouldThrowTaskNotFoundExceptionWhenTaskDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> dbService.getTask(1L));
    }
}