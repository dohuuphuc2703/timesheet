package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.PositionRequest;
import com.example.timesheet.dto.response.PositionResponse;
import com.example.timesheet.entity.Position;
import com.example.timesheet.mapper.PositionMapper;
import com.example.timesheet.repository.PositionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionService {
    PositionRepository positionRepository;
    PositionMapper positionMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PositionResponse createPosition(PositionRequest positionRequest) {
        Position position = positionMapper.toPosition(positionRequest);
        positionRepository.save(position);
        return positionMapper.toPositionResponse(position);
    }

    public List<PositionResponse> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        return positions.stream().map(positionMapper::toPositionResponse).collect(Collectors.toList());
    }

    public List<PositionResponse> searchPositionByName(String name) {
        List<Position> positions = positionRepository.findPositionsByNameContaining(name);
        return positions.stream().map(positionMapper::toPositionResponse).collect(Collectors.toList());
    }

    public PositionResponse getPositionByName(String name) {
        return this.positionMapper.toPositionResponse(
                positionRepository.findById(name).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PositionResponse updatePosition(String name, PositionRequest PositionRequest) {
        Position position = positionRepository.findById(name).orElseThrow();
        positionMapper.updatePosition(position, PositionRequest);
        return positionMapper.toPositionResponse(positionRepository.save(position));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePosition(String Position) {
        positionRepository.deleteById(Position);
    }
}
