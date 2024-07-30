package com.example.timesheet.entity;

import java.util.List;

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
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String note;

    @OneToMany(
            mappedBy = "timeSheet",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    List<TimeSheetDay> timesheetDays;
}
