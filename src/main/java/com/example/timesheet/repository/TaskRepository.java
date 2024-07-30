package com.example.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {}
