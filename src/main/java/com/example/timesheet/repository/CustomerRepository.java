package com.example.timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.timesheet.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {}
