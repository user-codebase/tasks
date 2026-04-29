package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMapperTest {

    private final TaskMapper mapper = new TaskMapper();

    @Test
    void shouldMapToTask() {
        // given
        TaskDto dto = new TaskDto(1L, "Test", "Content");

        // when
        Task task = mapper.mapToTask(dto);

        // then
        assertEquals(1L, task.getId());
        assertEquals("Test", task.getTitle());
        assertEquals("Content", task.getContent());
    }

    @Test
    void shouldMapToTaskDto() {
        // given
        Task task = new Task(1L, "Test", "Content");

        // when
        TaskDto dto = mapper.mapToTaskDto(task);

        // then
        assertEquals(1L, dto.getId());
        assertEquals("Test", dto.getTitle());
        assertEquals("Content", dto.getContent());
    }

    @Test
    void shouldMapToTaskDtoList() {
        // given
        List<Task> tasks = List.of(new Task(1L, "Test", "Content"));

        // when
        List<TaskDto> result = mapper.mapToTaskDtoList(tasks);

        // then
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Content", result.get(0).getContent());
    }

}