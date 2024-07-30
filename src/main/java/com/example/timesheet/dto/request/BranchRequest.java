package com.example.timesheet.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchRequest {
    String name;
    String displayName;
    String code;
    String workTimeM;
    String workTimeA;
}
