package com.example.timesheet.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.entity.TimeSheetDay;

@Repository
public interface TimeSheetDayRepository extends JpaRepository<TimeSheetDay, Integer> {
    Optional<TimeSheetDay> findByTimeSheetAndDate(TimeSheet timeSheet, LocalDate date);

    Page<TimeSheetDay> findByStatus(String status, Pageable pageable);

    Page<TimeSheetDay> findByStatusAndProjectId(String status, Integer projectId, Pageable pageable);

    void deleteByTimeSheetId(int timeSheetId);
}
