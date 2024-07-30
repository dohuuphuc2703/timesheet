package com.example.timesheet.service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.UserCreationRequest;
import com.example.timesheet.dto.request.UserUpdateRequest;
import com.example.timesheet.dto.response.UserResponse;
import com.example.timesheet.entity.*;
import com.example.timesheet.exception.AppException;
import com.example.timesheet.exception.ErrorCode;
import com.example.timesheet.mapper.UserMapper;
import com.example.timesheet.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    BranchRepository branchRepository;
    PositionRepository positionRepository;
    TimeSheetRepository timeSheetRepository;
    LeaveRequestRepository leaveRequestRepository;
    ProjectRepository projectRepository;
    TimeSheetService timeSheetService;
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        } else {
            User user = userMapper.toUser(request);
            TimeSheet timesheet = timeSheetService.createTimesheetForUser();
            List<Role> roles = roleRepository.findAllById(request.getRoles());
            List<Project> projects = projectRepository.findAllById(request.getProjects());
            Branch branch = branchRepository.findById(request.getBranch()).orElseThrow();
            Position position =
                    positionRepository.findById(request.getPosition()).orElseThrow();
            user.setBranch(branch);
            user.setPosition(position);
            user.setTimeSheet(timesheet);
            user.setRoles(new HashSet<>(roles));
            user.setProjects(new HashSet<>(projects));
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            return userRepository.save(user);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(int id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("User not found");
        });
        this.userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role> roles = roleRepository.findAllById(request.getRoles());
        List<Project> projects = projectRepository.findAllById(request.getProjects());
        Branch branch = branchRepository.findById(request.getBranch()).orElseThrow();
        Position position = positionRepository.findById(request.getPosition()).orElseThrow();
        user.setBranch(branch);
        user.setPosition(position);
        user.setRoles(new HashSet<>(roles));
        user.setProjects(new HashSet<>(projects));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> getAllUsers(
            String name,
            String branch,
            String position,
            String type,
            String level,
            Boolean isActive,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findByCriteria(name, branch, position, type, level, isActive, pageable);
        return userPage.map(userMapper::toUserResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(int id) {
        log.info("getUserById");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        }));
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @Transactional
    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            TimeSheet timeSheet = user.getTimeSheet();
            if (timeSheet != null) {
                List<TimeSheetDay> timeSheetDays = timeSheet.getTimesheetDays();
                if (timeSheetDays != null && !timeSheetDays.isEmpty()) {
                    timeSheetDays.clear();
                }
            }

            leaveRequestRepository.deleteByUserId(userId);
            userRepository.deleteById(userId);
        }
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsersByRole(String roleName) {
        var users = userRepository.findUsersByRoleName(roleName);
        return users.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
}
