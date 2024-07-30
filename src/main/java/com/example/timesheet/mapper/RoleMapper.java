package com.example.timesheet.mapper;

import org.mapstruct.Mapper;

import com.example.timesheet.dto.request.RoleRequest;
import com.example.timesheet.dto.response.RoleResponse;
import com.example.timesheet.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
