package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.PositionRequest;
import com.example.timesheet.dto.response.PositionResponse;
import com.example.timesheet.service.PositionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/positions"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    PositionService positionService;

    @PostMapping
    ApiResponse<PositionResponse> createPosition(@RequestBody PositionRequest positionRequest) {
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.createPosition(positionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PositionResponse>> getAllPositions() {
        return ApiResponse.<List<PositionResponse>>builder()
                .result(positionService.getAllPositions())
                .build();
    }

    @GetMapping({"/{positionName}"})
    ApiResponse<PositionResponse> getPositionByName(@PathVariable("positionName") String name) {
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.getPositionByName(name))
                .build();
    }

    @PutMapping({"/{positionName}"})
    ApiResponse<PositionResponse> updatePosition(
            @PathVariable("positionName") String name, @RequestBody PositionRequest PositionRequest) {
        return ApiResponse.<PositionResponse>builder()
                .result(positionService.updatePosition(name, PositionRequest))
                .build();
    }

    @GetMapping({"/search"})
    ApiResponse<List<PositionResponse>> searchPositions(@RequestParam String name) {
        return ApiResponse.<List<PositionResponse>>builder()
                .result(positionService.searchPositionByName(name))
                .build();
    }

    @DeleteMapping({"/{positionName}"})
    String deletePosition(@PathVariable("positionName") String name) {
        positionService.deletePosition(name);
        return "Deleted";
    }
}
