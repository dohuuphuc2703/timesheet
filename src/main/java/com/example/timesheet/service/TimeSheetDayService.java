package com.example.timesheet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.ManagementTimeSheetDayRequest;
import com.example.timesheet.dto.request.TimeSheetDayRequest;
import com.example.timesheet.dto.response.TimeSheetDayResponse;
import com.example.timesheet.entity.*;
import com.example.timesheet.exception.AppException;
import com.example.timesheet.exception.ErrorCode;
import com.example.timesheet.mapper.TimeSheetDayMapper;
import com.example.timesheet.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSheetDayService {
    TimeSheetDayRepository timeSheetDayRepository;
    TimeSheetDayMapper timeSheetDayMapper;
    ProjectRepository projectRepository;
    TaskRepository taskRepository;
    TimeSheetRepository timeSheetRepository;
    UserRepository userRepository;

    public TimeSheetDayResponse createTimeSheetDay(TimeSheetDayRequest timeSheetDayRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        TimeSheetDay timeSheetDay = timeSheetDayMapper.toTimeSheetDay(timeSheetDayRequest);
        TimeSheet timeSheet = user.getTimeSheet();
        LocalDate date = timeSheetDayRequest.getDate();
        timeSheetDayRepository.findByTimeSheetAndDate(timeSheet, date).ifPresent((existingTimeSheetDay) -> {
            throw new AppException(ErrorCode.TIME_SHEET_DAY_ALREADY_EXISTS);
        });
        Project project =
                projectRepository.findById(timeSheetDayRequest.getProject()).orElseThrow();
        Task task = taskRepository.findById(timeSheetDayRequest.getTask()).orElseThrow();
        timeSheetDay.setTimeSheet(timeSheet);
        timeSheetDay.setProject(project);
        timeSheetDay.setTask(task);
        timeSheetDay.setStatus("Pending");
        timeSheetDayRepository.save(timeSheetDay);
        return timeSheetDayMapper.toTimeSheetDayResponse(timeSheetDay);
    }

    public List<TimeSheetDayResponse> getAllTimeSheetDays() {
        List<TimeSheetDay> timeSheetDays = timeSheetDayRepository.findAll();
        return timeSheetDays.stream()
                .map(timeSheetDayMapper::toTimeSheetDayResponse)
                .collect(Collectors.toList());
    }

    public List<TimeSheetDayResponse> getTimeSheetDaysByWeek(String date) {
        LocalDate inputDate = LocalDate.parse(date);
        LocalDate startOfWeek = inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = inputDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<TimeSheetDay> timeSheetDays = timeSheetDayRepository.findAll().stream()
                .filter((day) -> {
                    return !day.getDate().isBefore(startOfWeek)
                            && !day.getDate().isAfter(endOfWeek);
                })
                .collect(Collectors.toList());

        return timeSheetDays.stream()
                .map(timeSheetDayMapper::toTimeSheetDayResponse)
                .collect(Collectors.toList());
    }

    public TimeSheetDayResponse getTimeSheetDayById(int id) {
        return timeSheetDayMapper.toTimeSheetDayResponse(
                timeSheetDayRepository.findById(id).orElseThrow());
    }

    public TimeSheetDayResponse updateTimeSheetDay(int id, TimeSheetDayRequest timeSheetDayRequest) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        TimeSheetDay timeSheetDay = timeSheetDayRepository.findById(id).orElseThrow();
        Project project =
                projectRepository.findById(timeSheetDayRequest.getProject()).orElseThrow();
        Task task = taskRepository.findById(timeSheetDayRequest.getTask()).orElseThrow();
        TimeSheet timeSheet = user.getTimeSheet();
        timeSheetDay.setTimeSheet(timeSheet);
        timeSheetDay.setProject(project);
        timeSheetDay.setTask(task);
        timeSheetDayMapper.updateTimeSheetDay(timeSheetDay, timeSheetDayRequest);
        return timeSheetDayMapper.toTimeSheetDayResponse(timeSheetDayRepository.save(timeSheetDay));
    }

    @PreAuthorize("hasRole('MANAGEMENT')")
    public TimeSheetDayResponse managementTimeSheetDay(
            int id, ManagementTimeSheetDayRequest managementTimeSheetDayRequest) {
        TimeSheetDay timeSheetDay = timeSheetDayRepository.findById(id).orElseThrow();
        timeSheetDay.setStatus(managementTimeSheetDayRequest.getStatus());
        return timeSheetDayMapper.toTimeSheetDayResponse(timeSheetDayRepository.save(timeSheetDay));
    }

    public void deleteTimeSheetDay(int id) {
        timeSheetDayRepository.deleteById(id);
    }

    public Page<TimeSheetDayResponse> getTimeSheetDays(Pageable pageable, String status, Integer project) {
        Page<TimeSheetDay> timeSheetDays;
        if (project != null) {
            timeSheetDays = timeSheetDayRepository.findByStatusAndProjectId(status, project, pageable);
        } else {
            timeSheetDays = timeSheetDayRepository.findByStatus(status, pageable);
        }

        return timeSheetDays.map(this::toTimeSheetDayResponseWithUser);
    }

    private TimeSheetDayResponse toTimeSheetDayResponseWithUser(TimeSheetDay timeSheetDay) {
        TimeSheetDayResponse response = timeSheetDayMapper.toTimeSheetDayResponse(timeSheetDay);
        if (timeSheetDay.getTimeSheet() != null) {
            User user = userRepository.findUsersByTimeSheetId(
                    timeSheetDay.getTimeSheet().getId());
            response.setByUsername(user.getUsername());
        }

        return response;
    }
}
