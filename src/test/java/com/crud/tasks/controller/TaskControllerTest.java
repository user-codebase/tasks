package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldFetchTasks() throws Exception {
        // Given
        Task task = new Task(1L, "Test", "Test desc");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test desc");

        when(service.getAllTasks()).thenReturn(List.of(task));
        when(taskMapper.mapToTaskDtoList(List.of(task))).thenReturn(List.of(taskDto));

        // When & Then
        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Test")))
                .andExpect(jsonPath("$[0].content", Matchers.is("Test desc")));
    }

    @Test
    void shouldFetchTask() throws Exception {
        // Given
        Task task = new Task(1L, "Test", "Test desc");
        TaskDto taskDto = new TaskDto(1L, "Test", "Test desc");

        when(service.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Test")))
                .andExpect(jsonPath("$.content", Matchers.is("Test desc")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        // When
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().isOk());

        // Then
        verify(service).deleteTask(1L);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test desc");
        Task task = new Task(1L, "Test", "Test desc");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String json = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Test")))
                .andExpect(jsonPath("$.content", Matchers.is("Test desc")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Test", "Test desc");
        Task task = new Task(1L, "Test", "Test desc");

        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String json = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test"))
                .andExpect(jsonPath("$.content").value("Test desc"));
    }

    @Test
    void shouldReturnBadRequestWhenTaskNotFound() throws Exception {
        // Given
        when(service.getTask(1L)).thenThrow(new TaskNotFoundException());

        // When & Then
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Task with given id doesn't exist"));
    }
}