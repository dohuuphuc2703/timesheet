package com.example.timesheet.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.UserCreationRequest;
import com.example.timesheet.dto.request.UserUpdateRequest;
import com.example.timesheet.dto.response.UserResponse;
import com.example.timesheet.entity.User;
import com.example.timesheet.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/users"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<Page<UserResponse>> getAllUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ApiResponse.<Page<UserResponse>>builder()
                .result(userService.getAllUsers(name, branch, position, type, level, isActive, page, size))
                .build();
    }

    @GetMapping({"/{userId}"})
    ApiResponse<UserResponse> getUser(@PathVariable("userId") int userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @PutMapping({"/{userId}"})
    ApiResponse<UserResponse> updateUser(@PathVariable int userId, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @DeleteMapping({"/{userId}"})
    String deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return "User deleted";
    }

    @GetMapping({"/my-info"})
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping({"/roles/{roleName}"})
    public ApiResponse<List<UserResponse>> getUsersByRole(@PathVariable String roleName) {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsersByRole(roleName))
                .build();
    }
}
