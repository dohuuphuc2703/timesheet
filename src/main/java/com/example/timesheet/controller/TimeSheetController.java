package com.example.timesheet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.response.TimeSheetResponse;
import com.example.timesheet.service.TimeSheetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/timeSheets"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSheetController {
    TimeSheetService timeSheetService;

    @GetMapping
    ApiResponse<TimeSheetResponse> getTimeSheetById(@RequestParam("date") String date) {
        return ApiResponse.<TimeSheetResponse>builder()
                .result(timeSheetService.getTimeSheetByUser(date))
                .build();
    }
}
