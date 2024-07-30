package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.BranchRequest;
import com.example.timesheet.dto.response.BranchResponse;
import com.example.timesheet.entity.Branch;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch toBranch(BranchRequest branchRequest);

    BranchResponse toBranchResponse(Branch branch);

    void updateBranch(@MappingTarget Branch branch, BranchRequest request);
}
