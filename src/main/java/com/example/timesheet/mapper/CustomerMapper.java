package com.example.timesheet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.timesheet.dto.request.CustomerRequest;
import com.example.timesheet.dto.response.CustomerResponse;
import com.example.timesheet.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequest customerRequest);

    CustomerResponse toCustomerResponse(Customer customer);

    void updateCustomer(@MappingTarget Customer customer, CustomerRequest customerRequest);
}
