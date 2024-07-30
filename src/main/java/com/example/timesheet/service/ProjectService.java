package com.example.timesheet.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.ProjectRequest;
import com.example.timesheet.dto.response.ProjectResponse;
import com.example.timesheet.dto.response.UserResponse;
import com.example.timesheet.entity.Customer;
import com.example.timesheet.entity.Project;
import com.example.timesheet.entity.Task;
import com.example.timesheet.entity.User;
import com.example.timesheet.mapper.ProjectMapper;
import com.example.timesheet.mapper.UserMapper;
import com.example.timesheet.repository.CustomerRepository;
import com.example.timesheet.repository.ProjectRepository;
import com.example.timesheet.repository.TaskRepository;
import com.example.timesheet.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectService {

    ProjectRepository projectRepository;
    ProjectMapper projectMapper;
    UserRepository userRepository;
    TaskRepository taskRepository;
    CustomerRepository customerRepository;
    UserMapper userMapper;

    @PreAuthorize("hasRole('MANAGEMENT')")
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project project = projectMapper.toProject(projectRequest);
        Customer customer =
                customerRepository.findById(projectRequest.getCustomer()).orElseThrow();
        List<Task> tasks = taskRepository.findAllById(projectRequest.getTasks());
        project.setCustomer(customer);
        project.setTasks(new HashSet<>(tasks));
        projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    public List<ProjectResponse> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(project -> {
                    Set<User> users = userRepository.findUsersByProjectId(project.getId());
                    ProjectResponse projectResponse = projectMapper.toProjectResponse(project);

                    Set<UserResponse> userResponses =
                            users.stream().map(userMapper::toUserResponse).collect(Collectors.toSet());

                    projectResponse.setUsers(userResponses);
                    return projectResponse;
                })
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(int id) {
        Project project = projectRepository.findById(id).orElseThrow();
        Set<User> users = userRepository.findUsersByProjectId(id);
        ProjectResponse projectResponse = projectMapper.toProjectResponse(project);

        Set<UserResponse> userResponses =
                users.stream().map(userMapper::toUserResponse).collect(Collectors.toSet());

        projectResponse.setUsers(userResponses);

        return projectResponse;
    }

    @PreAuthorize("hasRole('MANAGEMENT')")
    public ProjectResponse updateProject(int id, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(id).orElseThrow();
        Customer customer =
                customerRepository.findById(projectRequest.getCustomer()).orElseThrow();
        List<Task> tasks = taskRepository.findAllById(projectRequest.getTasks());
        project.setCustomer(customer);
        project.setTasks(new HashSet<>(tasks));
        this.projectMapper.updateProject(project, projectRequest);
        return this.projectMapper.toProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("hasRole('MANAGEMENT')")
    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }
}
