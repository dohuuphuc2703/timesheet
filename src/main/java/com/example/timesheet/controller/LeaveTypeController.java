package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.LeaveTypeRequest;
import com.example.timesheet.dto.response.LeaveTypeResponse;
import com.example.timesheet.service.LeaveTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/leaveTypes"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveTypeController {
    LeaveTypeService leaveTypeService;

    @PostMapping
    ApiResponse<LeaveTypeResponse> createLeaveType(@RequestBody LeaveTypeRequest leaveTypeRequest) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .result(leaveTypeService.createLeaveType(leaveTypeRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<LeaveTypeResponse>> getAllLeaveTypes() {
        return ApiResponse.<List<LeaveTypeResponse>>builder()
                .result(leaveTypeService.getAllLeaveTypes())
                .build();
    }

    @GetMapping({"/{leaveTypeId}"})
    ApiResponse<LeaveTypeResponse> getLeaveTypeById(@PathVariable("leaveTypeId") int id) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .result(leaveTypeService.getLeaveTypeById(id))
                .build();
    }

    @PutMapping({"/{leaveTypeId}"})
    ApiResponse<LeaveTypeResponse> updateLeaveType(
            @PathVariable("leaveTypeId") int id, @RequestBody LeaveTypeRequest leaveTypeRequest) {
        return ApiResponse.<LeaveTypeResponse>builder()
                .result(leaveTypeService.updateLeaveType(id, leaveTypeRequest))
                .build();
    }

    @DeleteMapping({"/{leaveTypeId}"})
    String deleteLeaveType(@PathVariable("leaveTypeId") int id) {
        leaveTypeService.deleteLeaveType(id);
        return "Deleted";
    }
}
