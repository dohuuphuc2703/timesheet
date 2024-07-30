package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.RoleRequest;
import com.example.timesheet.dto.response.RoleResponse;
import com.example.timesheet.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/roles"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRole() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRole())
                .build();
    }

    @DeleteMapping({"/{role}"})
    ApiResponse<Void> deleteRole(@PathVariable("role") String sole) {
        roleService.deleteRole(sole);
        return ApiResponse.<Void>builder().build();
    }
}
