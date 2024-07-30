package com.example.timesheet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.timesheet.dto.response.TaskResponse;
import com.example.timesheet.entity.Task;
import com.example.timesheet.mapper.TaskMapper;
import com.example.timesheet.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1);
        task.setName("Test Task");

        taskResponse = new TaskResponse();
        taskResponse.setId(1);
        taskResponse.setName("Test Task");
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task)).thenReturn(taskResponse);

        TaskResponse response = taskService.getTaskById(1);

        assertEquals(1, response.getId());
        assertEquals("Test Task", response.getName());
    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1));
    }
}

