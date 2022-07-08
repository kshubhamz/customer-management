package com.redhatschool.customermanagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redhatschool.customermanagement.dao.CustomerRepository;
import com.redhatschool.customermanagement.dto.CustomerDto;
import com.redhatschool.customermanagement.entity.Customer;
import com.redhatschool.customermanagement.exception.NotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	@Override
	public Customer getCustomerById(Long customerId) {
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if (customerOpt.isEmpty())
			throw new NotFoundException("Customer with Id " + customerId + " doesn't exist.");
		return customerOpt.get();
	}

	@Override
	public Map<String, Object> deleteCustomerById(Long customerId) {
		Customer customer = getCustomerById(customerId);
		customerRepository.deleteById(customerId);
		return Map.of("message", "Deleted successfully.",
				"customer", customer);
	}

	@Override
	public Customer updateCustomerById(Long customerId, CustomerDto dto) {
		Customer customer = getCustomerById(customerId);
		customer.setBillAmount(dto.getBill());
		customer.setCustomerName(dto.getName());
		if (dto.getBillingDate() != null)
			customer.setBillingDate(LocalDateTime.parse(dto.getBillingDate() + " 00:00", pattern));
		return customerRepository.save(customer);
	}

	@Override
	public Customer createCustomer(CustomerDto customerDto) {
		Customer newCustomer;
		if (customerDto.getBillingDate() != null) {
			LocalDateTime date = LocalDateTime.parse(customerDto.getBillingDate() + " 00:00", pattern);
			newCustomer = new Customer(customerDto.getName(), customerDto.getBill(), date);
		} else
			newCustomer = new Customer(customerDto.getName(), customerDto.getBill(), LocalDateTime.now());
		return customerRepository.save(newCustomer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> getAllCustomersWithinBillingDate(LocalDateTime dateTime1, LocalDateTime dateTime2) {
		return customerRepository.findCustomersBetweenBillingDate(dateTime1, dateTime2);
	}

}
