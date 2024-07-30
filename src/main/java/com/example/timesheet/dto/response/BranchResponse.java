package com.example.timesheet.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchResponse {
    String name;
    String displayName;
    String code;
    String workTimeM;
    String workTimeA;
}
