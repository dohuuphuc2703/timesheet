package com.example.timesheet.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveReqResponse {
    int id;
    UserResponse user;
    LeaveTypeResponse leaveType;
    LocalDate date;
    String status;
    String note;
    int hours;
    String timeType;
    String typeRequest;
    Boolean isLate;
}
