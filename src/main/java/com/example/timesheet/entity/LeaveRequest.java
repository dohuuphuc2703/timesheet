package com.example.timesheet.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    User user;

    @ManyToOne
    LeaveType leaveType;

    LocalDate date;
    String status;
    String typeRequest;
    String timeType;
    String note;
    int hours;
    Boolean isLate;
}
