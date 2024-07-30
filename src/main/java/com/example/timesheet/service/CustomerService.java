package com.example.timesheet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.timesheet.dto.request.CustomerRequest;
import com.example.timesheet.dto.response.CustomerResponse;
import com.example.timesheet.entity.Customer;
import com.example.timesheet.mapper.CustomerMapper;
import com.example.timesheet.repository.CustomerRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.toCustomer(customerRequest);
        customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customerMapper::toCustomerResponse).collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(int id) {
        return customerMapper.toCustomerResponse(customerRepository.findById(id).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse updateCustomer(int id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customerMapper.updateCustomer(customer, customerRequest);
        return this.customerMapper.toCustomerResponse(customerRepository.save(customer));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomer(int id) {
        customerRepository.deleteById(id);
    }
}
