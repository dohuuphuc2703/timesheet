package com.example.timesheet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.timesheet.dto.request.ApiResponse;
import com.example.timesheet.dto.request.CustomerRequest;
import com.example.timesheet.dto.response.CustomerResponse;
import com.example.timesheet.service.CustomerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping({"/customers"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerService customerService;

    @PostMapping
    ApiResponse<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.createCustomer(customerRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<CustomerResponse>> getAllCustomers() {
        return ApiResponse.<List<CustomerResponse>>builder()
                .result(customerService.getAllCustomers())
                .build();
    }

    @GetMapping({"/{customerId}"})
    ApiResponse<CustomerResponse> getCustomerById(@PathVariable("customerId") int id) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.getCustomerById(id))
                .build();
    }

    @PutMapping({"/{customerId}"})
    ApiResponse<CustomerResponse> updateCustomer(
            @PathVariable("customerId") int id, @RequestBody CustomerRequest customerRequest) {
        return ApiResponse.<CustomerResponse>builder()
                .result(customerService.updateCustomer(id, customerRequest))
                .build();
    }

    @DeleteMapping({"/{customerId}"})
    String deleteCustomer(@PathVariable("customerId") int id) {
        customerService.deleteCustomer(id);
        return "Deleted";
    }
}
