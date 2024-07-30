package com.example.timesheet.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LeaveTypeResponse {
    int id;
    String name;
    int countDay;
    String type;
}
