package com.redhatschool.customermanagement.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.redhatschool.customermanagement.dto.CustomerDto;
import com.redhatschool.customermanagement.entity.Customer;
import com.redhatschool.customermanagement.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable Long id) {
		return customerService.getCustomerById(id);
	}
	
	@GetMapping("/within-billing-date")
	public List<Customer> getAllCustomersWithinBillingDate(@RequestParam("start-date") String date1,
			@RequestParam("end-date") String date2) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime localDate1 = LocalDateTime.parse(date1 + " 00:00", pattern);
		LocalDateTime localDate2 = LocalDateTime.parse(date2 + " 00:00", pattern);
		return customerService.getAllCustomersWithinBillingDate(localDate1, localDate2);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Customer createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		return customerService.createCustomer(customerDto);
	}
	
	@PutMapping("/{id}")
	public Customer updateCustomerById(@Valid @RequestBody CustomerDto customerDto,
			@PathVariable("id") Long customerId) {
		return customerService.updateCustomerById(customerId, customerDto);
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Object> deleteCustomerById(@PathVariable Long id) {
		return customerService.deleteCustomerById(id);
	}

}
