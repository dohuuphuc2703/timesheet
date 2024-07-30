package com.example.timesheet.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.ManagementTimeSheetDayRequest;
import com.example.timesheet.dto.request.TimeSheetDayRequest;
import com.example.timesheet.dto.response.TimeSheetDayResponse;
import com.example.timesheet.service.TimeSheetDayService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/timeSheetDays"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSheetDayController {
    TimeSheetDayService timeSheetDayService;

    @PostMapping
    ApiResponse<TimeSheetDayResponse> createTimeSheetDay(@RequestBody TimeSheetDayRequest timeSheetDayRequest) {
        return ApiResponse.<TimeSheetDayResponse>builder()
                .result(timeSheetDayService.createTimeSheetDay(timeSheetDayRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<TimeSheetDayResponse>> getAllTimeSheetDays() {
        return ApiResponse.<List<TimeSheetDayResponse>>builder()
                .result(timeSheetDayService.getAllTimeSheetDays())
                .build();
    }

    @GetMapping({"/week"})
    ApiResponse<List<TimeSheetDayResponse>> getTimeSheetDaysByWeek(@RequestParam("date") String date) {
        return ApiResponse.<List<TimeSheetDayResponse>>builder()
                .result(timeSheetDayService.getTimeSheetDaysByWeek(date))
                .build();
    }

    @GetMapping({"/{timeSheetDayId}"})
    ApiResponse<TimeSheetDayResponse> getTimeSheetDayById(@PathVariable("timeSheetDayId") int id) {
        return ApiResponse.<TimeSheetDayResponse>builder()
                .result(timeSheetDayService.getTimeSheetDayById(id))
                .build();
    }

    @PutMapping({"/{timeSheetDayId}"})
    ApiResponse<TimeSheetDayResponse> updateTimeSheetDay(
            @PathVariable("timeSheetDayId") int id, @RequestBody TimeSheetDayRequest timeSheetDayRequest) {
        return ApiResponse.<TimeSheetDayResponse>builder()
                .result(timeSheetDayService.updateTimeSheetDay(id, timeSheetDayRequest))
                .build();
    }

    @PutMapping({"/management/{timeSheetDayId}"})
    ApiResponse<TimeSheetDayResponse> managementTimeSheetDay(
            @PathVariable("timeSheetDayId") int id, @RequestBody ManagementTimeSheetDayRequest managementTimeSheetDay) {
        return ApiResponse.<TimeSheetDayResponse>builder()
                .result(timeSheetDayService.managementTimeSheetDay(id, managementTimeSheetDay))
                .build();
    }

    @DeleteMapping({"/{timeSheetDayId}"})
    String deleteTimeSheetDay(@PathVariable("timeSheetDayId") int id) {
        timeSheetDayService.deleteTimeSheetDay(id);
        return "Deleted";
    }

    @GetMapping({"/management"})
    ApiResponse<Page<TimeSheetDayResponse>> getPendingTimeSheetDays(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String status,
            Integer project) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.<Page<TimeSheetDayResponse>>builder()
                .result(timeSheetDayService.getTimeSheetDays(pageable, status, project))
                .build();
    }
}
