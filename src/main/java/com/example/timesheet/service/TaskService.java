package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.TaskRequest;
import com.example.timesheet.dto.response.TaskResponse;
import com.example.timesheet.entity.Task;
import com.example.timesheet.mapper.TaskMapper;
import com.example.timesheet.repository.TaskRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskService {
    TaskRepository taskRepository;
    TaskMapper taskMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toTask(taskRequest);
        taskRepository.save(task);
        return taskMapper.toTaskResponse(task);
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::toTaskResponse).collect(Collectors.toList());
    }

    public TaskResponse getTaskById(int id) {
        return taskMapper.toTaskResponse(taskRepository.findById(id).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public TaskResponse updateTask(int id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow();
        taskMapper.updateTask(task, taskRequest);
        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
