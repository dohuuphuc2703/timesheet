package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.LeaveTypeRequest;
import com.example.timesheet.dto.response.LeaveTypeResponse;
import com.example.timesheet.entity.LeaveType;

@Mapper(componentModel = "spring")
public interface LeaveTypeMapper {
    LeaveType toLeaveType(LeaveTypeRequest leaveTypeRequest);

    LeaveTypeResponse toLeaveTypeResponse(LeaveType leaveType);

    void updateLeaveType(@MappingTarget LeaveType leaveType, LeaveTypeRequest leaveTypeRequest);
}
