package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.DmVsRequest;
import com.example.timesheet.dto.request.ManagementLeaveRequest;
import com.example.timesheet.dto.request.OffRequest;
import com.example.timesheet.dto.request.RemoteOnsiteRequest;
import com.example.timesheet.dto.response.LeaveReqResponse;
import com.example.timesheet.entity.LeaveRequest;
import com.example.timesheet.entity.LeaveType;
import com.example.timesheet.entity.User;
import com.example.timesheet.exception.AppException;
import com.example.timesheet.exception.ErrorCode;
import com.example.timesheet.mapper.LeaveRequestMapper;
import com.example.timesheet.repository.LeaveRequestRepository;
import com.example.timesheet.repository.LeaveTypeRepository;
import com.example.timesheet.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestService {
    LeaveRequestRepository leaveRequestRepository;
    LeaveRequestMapper leaveRequestMapper;
    UserRepository userRepository;
    LeaveTypeRepository leaveTypeRepository;

    public LeaveReqResponse createOffRequest(OffRequest offRequest) {
        LeaveRequest leaveRequest = leaveRequestMapper.toLeaveRequest(offRequest);
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        LeaveType leaveType =
                leaveTypeRepository.findById(offRequest.getLeaveType()).orElseThrow();
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setUser(user);
        leaveRequest.setTypeRequest("Off");
        leaveRequest.setStatus("pending");
        leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toLeaveReqResponse(leaveRequest);
    }

    public LeaveReqResponse createDmVsRequest(DmVsRequest dmVsRequest) {
        LeaveRequest leaveRequest = leaveRequestMapper.toLeaveRequest(dmVsRequest);
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        leaveRequest.setUser(user);
        leaveRequest.setTypeRequest("DS_VM");
        leaveRequest.setStatus("pending");
        leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toLeaveReqResponse(leaveRequest);
    }

    public LeaveReqResponse createRemoteOnsiteRequest(RemoteOnsiteRequest remoteOnsiteRequest) {
        LeaveRequest leaveRequest = this.leaveRequestMapper.toLeaveRequest(remoteOnsiteRequest);
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        leaveRequest.setUser(user);
        leaveRequest.setTypeRequest("Remote_Onsite");
        leaveRequest.setStatus("pending");
        leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toLeaveReqResponse(leaveRequest);
    }

    public List<LeaveReqResponse> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = this.leaveRequestRepository.findAll();

        return leaveRequests.stream()
                .map(leaveRequestMapper::toLeaveReqResponse)
                .collect(Collectors.toList());
    }

    public List<LeaveReqResponse> getLeaveRequestsByProjects(List<Integer> projectIds) {
        List<LeaveRequest> leaveRequests = this.leaveRequestRepository.findByUserProjectsIdIn(projectIds);

        return leaveRequests.stream()
                .map(leaveRequestMapper::toLeaveReqResponse)
                .collect(Collectors.toList());
    }

    public List<LeaveReqResponse> getAllLeaveRequestsByUser() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> {
            return new AppException(ErrorCode.USER_NOT_EXISTED);
        });
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByUserId(user.getId());

        return leaveRequests.stream()
                .map(leaveRequestMapper::toLeaveReqResponse)
                .collect(Collectors.toList());
    }

    public Page<LeaveReqResponse> getLeaveRequests(String status, Pageable pageable) {
        Page<LeaveRequest> leaveRequests = leaveRequestRepository.findByStatus(status, pageable);

        Page<LeaveReqResponse> leaveReqResponsePage = leaveRequests.map(leaveRequestMapper::toLeaveReqResponse);
        return leaveReqResponsePage;
    }

    @PreAuthorize("hasRole('MANAGEMENT')")
    public LeaveReqResponse managementLeaveRequest(int id, ManagementLeaveRequest managementLeaveRequest) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow();
        leaveRequest.setStatus(managementLeaveRequest.getStatus());
        return leaveRequestMapper.toLeaveReqResponse(leaveRequestRepository.save(leaveRequest));
    }
}
