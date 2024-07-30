package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.LeaveTypeRequest;
import com.example.timesheet.dto.response.LeaveTypeResponse;
import com.example.timesheet.entity.LeaveType;
import com.example.timesheet.mapper.LeaveTypeMapper;
import com.example.timesheet.repository.LeaveTypeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveTypeService {

    LeaveTypeRepository leaveTypeRepository;
    LeaveTypeMapper leaveTypeMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public LeaveTypeResponse createLeaveType(LeaveTypeRequest leaveTypeRequest) {
        LeaveType LeaveType = leaveTypeMapper.toLeaveType(leaveTypeRequest);
        leaveTypeRepository.save(LeaveType);
        return this.leaveTypeMapper.toLeaveTypeResponse(LeaveType);
    }

    public List<LeaveTypeResponse> getAllLeaveTypes() {
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        return leaveTypes.stream().map(leaveTypeMapper::toLeaveTypeResponse).collect(Collectors.toList());
    }

    public LeaveTypeResponse getLeaveTypeById(int id) {
        return leaveTypeMapper.toLeaveTypeResponse(
                leaveTypeRepository.findById(id).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public LeaveTypeResponse updateLeaveType(int id, LeaveTypeRequest leaveTypeRequest) {
        LeaveType leaveType = leaveTypeRepository.findById(id).orElseThrow();
        leaveTypeMapper.updateLeaveType(leaveType, leaveTypeRequest);
        return leaveTypeMapper.toLeaveTypeResponse(leaveTypeRepository.save(leaveType));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLeaveType(int id) {
        leaveTypeRepository.deleteById(id);
    }
}
