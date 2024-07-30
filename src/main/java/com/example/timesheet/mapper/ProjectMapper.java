package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.ProjectRequest;
import com.example.timesheet.dto.response.ProjectResponse;
import com.example.timesheet.entity.Project;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class})
public interface ProjectMapper {
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toProject(ProjectRequest projectRequest);

    ProjectResponse toProjectResponse(Project project);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateProject(@MappingTarget Project project, ProjectRequest projectRequest);
}
