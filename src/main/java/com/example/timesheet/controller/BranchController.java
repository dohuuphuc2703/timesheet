package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.BranchRequest;
import com.example.timesheet.dto.response.BranchResponse;
import com.example.timesheet.service.BranchService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/branches"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BranchController {
    BranchService branchService;

    @PostMapping
    ApiResponse<BranchResponse> createBranch(@RequestBody BranchRequest branchRequest) {
        return ApiResponse.<BranchResponse>builder()
                .result(branchService.createBranch(branchRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<BranchResponse>> getAllBranches() {
        return ApiResponse.<List<BranchResponse>>builder()
                .result(branchService.getAllBranches())
                .build();
    }

    @GetMapping({"/{branchName}"})
    ApiResponse<BranchResponse> getBranchByName(@PathVariable("branchName") String name) {
        return ApiResponse.<BranchResponse>builder()
                .result(this.branchService.getBranchByName(name))
                .build();
    }

    @PutMapping({"/{branchName}"})
    ApiResponse<BranchResponse> updateBranch(
            @PathVariable("branchName") String name, @RequestBody BranchRequest branchRequest) {
        return ApiResponse.<BranchResponse>builder()
                .result(branchService.updateBranch(name, branchRequest))
                .build();
    }

    @GetMapping({"/search"})
    ApiResponse<List<BranchResponse>> searchBranches(@RequestParam String name) {
        return ApiResponse.<List<BranchResponse>>builder()
                .result(branchService.searchBranchByName(name))
                .build();
    }

    @DeleteMapping({"/{branchName}"})
    String deleteBranch(@PathVariable("branchName") String name) {
        branchService.deleteBranch(name);
        return "Deleted";
    }
}
