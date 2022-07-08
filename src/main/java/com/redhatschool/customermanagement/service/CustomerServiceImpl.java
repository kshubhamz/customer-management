package com.redhatschool.customermanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redhatschool.customermanagement.dao.CustomerRepository;
import com.redhatschool.customermanagement.dto.CustomerDto;
import com.redhatschool.customermanagement.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer getCustomerById(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> deleteCustomerById(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer updateCustomerById(Long customerId, CustomerDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer createCustomer(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getAllCustomersWithinBillingDate(LocalDateTime dateTime1, LocalDateTime dateTime2) {
		// TODO Auto-generated method stub
		return null;
	}

}
