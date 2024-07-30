package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.TimeSheetDayRequest;
import com.example.timesheet.dto.response.TimeSheetDayResponse;
import com.example.timesheet.entity.TimeSheetDay;

@Mapper(componentModel = "spring")
public interface TimeSheetDayMapper {

    @Mapping(target = "timeSheet", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "task", ignore = true)
    TimeSheetDay toTimeSheetDay(TimeSheetDayRequest TimeSheetDayRequest);

    TimeSheetDayResponse toTimeSheetDayResponse(TimeSheetDay TimeSheetDay);

    @Mapping(target = "timeSheet", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "task", ignore = true)
    void updateTimeSheetDay(@MappingTarget TimeSheetDay TimeSheetDay, TimeSheetDayRequest TimeSheetDayRequest);
}
