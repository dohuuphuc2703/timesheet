package com.example.timesheet.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.*;
import com.example.timesheet.dto.response.LeaveReqResponse;
import com.example.timesheet.service.LeaveRequestService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/leaveRequest"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestController {

    LeaveRequestService leaveRequestService;

    @PostMapping({"/off"})
    ApiResponse<LeaveReqResponse> createOffRequest(@RequestBody OffRequest offRequest) {
        return ApiResponse.<LeaveReqResponse>builder()
                .result(leaveRequestService.createOffRequest(offRequest))
                .build();
    }

    @PostMapping({"/dm_vs"})
    ApiResponse<LeaveReqResponse> createDmVsRequest(@RequestBody DmVsRequest dmVsRequest) {
        return ApiResponse.<LeaveReqResponse>builder()
                .result(leaveRequestService.createDmVsRequest(dmVsRequest))
                .build();
    }

    @PostMapping({"re_on"})
    ApiResponse<LeaveReqResponse> createRemoteOnsiteRequest(@RequestBody RemoteOnsiteRequest remoteOnsiteRequest) {
        return ApiResponse.<LeaveReqResponse>builder()
                .result(leaveRequestService.createRemoteOnsiteRequest(remoteOnsiteRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<LeaveReqResponse>> getAllLeaveRequests() {
        return ApiResponse.<List<LeaveReqResponse>>builder()
                .result(leaveRequestService.getAllLeaveRequests())
                .build();
    }

    @GetMapping({"/byUser"})
    ApiResponse<List<LeaveReqResponse>> getLeaveRequestsByUser() {
        return ApiResponse.<List<LeaveReqResponse>>builder()
                .result(leaveRequestService.getAllLeaveRequestsByUser())
                .build();
    }

    @GetMapping({"/management"})
    ApiResponse<Page<LeaveReqResponse>> getPendingLeaveRequests(String status, Pageable pageable) {
        return ApiResponse.<Page<LeaveReqResponse>>builder()
                .result(this.leaveRequestService.getLeaveRequests(status, pageable))
                .build();
    }

    @PutMapping({"/management/{leaveRequestId}"})
    ApiResponse<LeaveReqResponse> managementTimeSheetDay(
            @PathVariable("leaveRequestId") int id, @RequestBody ManagementLeaveRequest managementLeaveRequest) {
        return ApiResponse.<LeaveReqResponse>builder()
                .result(leaveRequestService.managementLeaveRequest(id, managementLeaveRequest))
                .build();
    }

    @GetMapping({"/projects"})
    public ApiResponse<List<LeaveReqResponse>> getLeaveRequestsByProjects(@RequestParam List<Integer> projectIds) {
        return ApiResponse.<List<LeaveReqResponse>>builder()
                .result(leaveRequestService.getLeaveRequestsByProjects(projectIds))
                .build();
    }
}
