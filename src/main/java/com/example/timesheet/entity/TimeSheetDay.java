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
public class TimeSheetDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    TimeSheet timeSheet;

    @ManyToOne
    Project project;

    @ManyToOne
    Task task;

    String note;
    int workingTime;
    String status;
}
