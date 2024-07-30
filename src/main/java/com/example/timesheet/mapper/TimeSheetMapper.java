package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.timesheet.dto.response.TimeSheetResponse;
import com.example.timesheet.entity.TimeSheet;

@Mapper(componentModel = "spring")
public interface TimeSheetMapper {
    @Mapping(target = "timeSheetDays", source = "timesheetDays")
    TimeSheetResponse toTimeSheetResponse(TimeSheet timeSheet);
}
