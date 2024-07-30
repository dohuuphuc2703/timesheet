package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.UserCreationRequest;
import com.example.timesheet.dto.request.UserUpdateRequest;
import com.example.timesheet.dto.response.UserResponse;
import com.example.timesheet.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "timeSheet", ignore = true)
    @Mapping(target = "projects", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "timeSheet", ignore = true)
    @Mapping(target = "projects", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
