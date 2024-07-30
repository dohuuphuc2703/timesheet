package com.example.timesheet.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeSheetDayResponse {
    int id;
    LocalDate date;
    String note;
    int workingTime;
    ProjectResponse project;
    TaskResponse task;
    String status;
    String byUsername;
}
