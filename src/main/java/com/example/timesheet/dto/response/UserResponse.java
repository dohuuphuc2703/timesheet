package com.example.timesheet.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String username;
    String name;
    LocalDate dob;
    String sex;
    String type;
    String level;
    String isActive;
    BranchResponse branch;
    PositionResponse position;
    TimeSheetResponse timeSheet;
    Set<RoleResponse> roles;
    Set<ProjectResponse> projects;
}
