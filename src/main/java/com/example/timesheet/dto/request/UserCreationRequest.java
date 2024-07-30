package com.example.timesheet.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;
    String password;
    String name;
    LocalDate dob;
    String sex;
    String type;
    String level;
    String isActive;
    String branch;
    String position;
    List<String> roles;
    List<Integer> projects;
}
