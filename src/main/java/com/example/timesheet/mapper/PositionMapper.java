package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.PositionRequest;
import com.example.timesheet.dto.response.PositionResponse;
import com.example.timesheet.entity.Position;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    Position toPosition(PositionRequest positionRequest);

    PositionResponse toPositionResponse(Position position);

    void updatePosition(@MappingTarget Position position, PositionRequest request);
}
