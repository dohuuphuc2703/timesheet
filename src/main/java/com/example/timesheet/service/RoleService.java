package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.RoleRequest;
import com.example.timesheet.dto.response.RoleResponse;
import com.example.timesheet.entity.Role;
import com.example.timesheet.mapper.RoleMapper;
import com.example.timesheet.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);
        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRole() {
        List<Role> roles = this.roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }
}
