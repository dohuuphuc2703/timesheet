package com.example.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {}
