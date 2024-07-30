package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.TaskRequest;
import com.example.timesheet.dto.response.TaskResponse;
import com.example.timesheet.service.TaskService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/tasks"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {
    TaskService taskService;

    @PostMapping
    ApiResponse<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.createTask(taskRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<TaskResponse>> getAllTasks() {
        return ApiResponse.<List<TaskResponse>>builder()
                .result(taskService.getAllTasks())
                .build();
    }

    @GetMapping({"/{taskId}"})
    ApiResponse<TaskResponse> getTaskById(@PathVariable("taskId") int id) {
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.getTaskById(id))
                .build();
    }

    @PutMapping({"/{taskId}"})
    ApiResponse<TaskResponse> updateTask(@PathVariable("taskId") int id, @RequestBody TaskRequest taskRequest) {
        return ApiResponse.<TaskResponse>builder()
                .result(taskService.updateTask(id, taskRequest))
                .build();
    }

    @DeleteMapping({"/{taskId}"})
    String deleteTask(@PathVariable("taskId") int id) {
        taskService.deleteTask(id);
        return "Deleted";
    }
}
