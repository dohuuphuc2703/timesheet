package com.example.timesheet.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.response.TimeSheetResponse;
import com.example.timesheet.entity.TimeSheet;
import com.example.timesheet.entity.TimeSheetDay;
import com.example.timesheet.entity.User;
import com.example.timesheet.exception.AppException;
import com.example.timesheet.exception.ErrorCode;
import com.example.timesheet.mapper.TimeSheetDayMapper;
import com.example.timesheet.mapper.TimeSheetMapper;
import com.example.timesheet.repository.TimeSheetRepository;
import com.example.timesheet.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSheetService {
    TimeSheetRepository timeSheetRepository;
    TimeSheetMapper timeSheetMapper;
    UserRepository userRepository;
    TimeSheetDayMapper timeSheetDayMapper;

    public TimeSheet createTimesheetForUser() {
        TimeSheet timesheet = new TimeSheet();
        return timeSheetRepository.save(timesheet);
    }

    public TimeSheetResponse getTimeSheetByUser(String date) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        TimeSheet timeSheet =
                timeSheetRepository.findById(user.getTimeSheet().getId()).orElseThrow();
        LocalDate inputDate = LocalDate.parse(date);
        LocalDate startOfWeek = inputDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = inputDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<TimeSheetDay> sortedTimeSheetDays = timeSheet.getTimesheetDays().stream()
                .filter((day) -> {
                    return !day.getDate().isBefore(startOfWeek)
                            && !day.getDate().isAfter(endOfWeek);
                })
                .collect(Collectors.toList());
        sortedTimeSheetDays.stream()
                .map(timeSheetDayMapper::toTimeSheetDayResponse)
                .collect(Collectors.toList());
        timeSheet.setTimesheetDays(sortedTimeSheetDays);
        return this.timeSheetMapper.toTimeSheetResponse(timeSheet);
    }
}
