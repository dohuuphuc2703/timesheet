package com.example.timesheet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    List<Branch> findBranchesByNameContaining(String name);
}
