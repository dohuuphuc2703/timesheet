package com.example.timesheet.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByUserId(int id);

    @Query("SELECT lr FROM LeaveRequest lr WHERE :status IS NULL OR lr.status LIKE %:status%")
    Page<LeaveRequest> findByStatus(@Param("status") String status, Pageable pageable);

    List<LeaveRequest> findByUserProjectsIdIn(List<Integer> projectIds);

    void deleteByUserId(int userId);
}
