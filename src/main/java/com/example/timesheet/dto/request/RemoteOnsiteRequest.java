package com.example.timesheet.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoteOnsiteRequest {
    LocalDate date;
    int user;
    String note;
    String timeType;
}
