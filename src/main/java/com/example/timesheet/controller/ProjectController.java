package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.ProjectRequest;
import com.example.timesheet.dto.response.ProjectResponse;
import com.example.timesheet.service.ProjectService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/projects"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {
    ProjectService projectService;

    @PostMapping
    ApiResponse<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        return ApiResponse.<ProjectResponse>builder()
                .result(projectService.createProject(projectRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProjectResponse>> getAllProjects() {
        return ApiResponse.<List<ProjectResponse>>builder()
                .result(projectService.getAllProjects())
                .build();
    }

    @GetMapping({"/{projectId}"})
    ApiResponse<ProjectResponse> getProjectById(@PathVariable("projectId") int id) {
        return ApiResponse.<ProjectResponse>builder()
                .result(projectService.getProjectById(id))
                .build();
    }

    @PutMapping({"/{projectId}"})
    ApiResponse<ProjectResponse> updateProject(
            @PathVariable("projectId") int id, @RequestBody ProjectRequest projectRequest) {
        return ApiResponse.<ProjectResponse>builder()
                .result(projectService.updateProject(id, projectRequest))
                .build();
    }

    @DeleteMapping({"/{projectId}"})
    String deleteProject(@PathVariable("projectId") int id) {
        projectService.deleteProject(id);
        return "Deleted";
    }
}
