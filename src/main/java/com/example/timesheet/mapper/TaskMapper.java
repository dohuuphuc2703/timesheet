package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.TaskRequest;
import com.example.timesheet.dto.response.TaskResponse;
import com.example.timesheet.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toTask(TaskRequest taskRequest);

    TaskResponse toTaskResponse(Task task);

    void updateTask(@MappingTarget Task task, TaskRequest taskRequest);
}
