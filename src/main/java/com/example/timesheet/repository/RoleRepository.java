package com.example.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
