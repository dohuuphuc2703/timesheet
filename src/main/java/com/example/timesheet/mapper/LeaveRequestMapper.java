package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.timesheet.dto.request.DmVsRequest;
import com.example.timesheet.dto.request.OffRequest;
import com.example.timesheet.dto.request.RemoteOnsiteRequest;
import com.example.timesheet.dto.response.LeaveReqResponse;
import com.example.timesheet.entity.LeaveRequest;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "leaveType", ignore = true)
    LeaveRequest toLeaveRequest(OffRequest offRequest);

    @Mapping(target = "user", ignore = true)
    LeaveRequest toLeaveRequest(DmVsRequest dmVsRequest);

    @Mapping(target = "user", ignore = true)
    LeaveRequest toLeaveRequest(RemoteOnsiteRequest remoteOnsiteRequest);

    LeaveReqResponse toLeaveReqResponse(LeaveRequest leaveRequest);
}
