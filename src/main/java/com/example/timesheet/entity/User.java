package com.example.timesheet.entity;

import java.time.LocalDate;
import java.util.Set;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String username;
    String password;
    String name;
    LocalDate dob;
    String sex;
    String type;
    String level;
    String isActive;

    @ManyToOne
    Branch branch;

    @ManyToOne
    Position position;

    @OneToOne(
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    TimeSheet timeSheet;

    @ManyToMany
    Set<Role> roles;

    @ManyToMany
    Set<Project> projects;
}
