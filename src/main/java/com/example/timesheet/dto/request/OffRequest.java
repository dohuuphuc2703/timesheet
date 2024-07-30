package com.example.timesheet.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OffRequest {
    LocalDate date;
    int user;
    int leaveType;
    String timeType;
    String note;
}
