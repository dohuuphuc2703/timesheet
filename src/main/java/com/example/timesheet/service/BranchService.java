package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.BranchRequest;
import com.example.timesheet.dto.response.BranchResponse;
import com.example.timesheet.entity.Branch;
import com.example.timesheet.mapper.BranchMapper;
import com.example.timesheet.repository.BranchRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BranchService {
    BranchRepository branchRepository;
    BranchMapper branchMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public BranchResponse createBranch(BranchRequest branchRequest) {
        Branch branch = branchMapper.toBranch(branchRequest);
        branchRepository.save(branch);
        return branchMapper.toBranchResponse(branch);
    }

    public List<BranchResponse> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream().map(branchMapper::toBranchResponse).collect(Collectors.toList());
    }

    public List<BranchResponse> searchBranchByName(String name) {
        List<Branch> branches = branchRepository.findBranchesByNameContaining(name);
        return branches.stream().map(branchMapper::toBranchResponse).collect(Collectors.toList());
    }

    public BranchResponse getBranchByName(String name) {
        return branchMapper.toBranchResponse(branchRepository.findById(name).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BranchResponse updateBranch(String name, BranchRequest branchRequest) {
        Branch branch = branchRepository.findById(name).orElseThrow();
        branchMapper.updateBranch(branch, branchRequest);
        return branchMapper.toBranchResponse(branchRepository.save(branch));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBranch(String branch) {
        branchRepository.deleteById(branch);
    }
}
