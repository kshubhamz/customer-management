package com.redhatschool.customermanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.redhatschool.customermanagement.dto.CustomerDto;
import com.redhatschool.customermanagement.entity.Customer;

public interface CustomerService {
	Customer getCustomerById(Long customerId);

	Map<String, Object> deleteCustomerById(Long customerId);

	Customer updateCustomerById(Long customerId, CustomerDto dto);

	Customer createCustomer(CustomerDto customerDto);

	List<Customer> getAllCustomers();

	List<Customer> getAllCustomersWithinBillingDate(LocalDateTime dateTime1, LocalDateTime dateTime2);
}
