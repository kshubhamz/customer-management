package com.redhatschool.customermanagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.redhatschool.customermanagement.dao.CustomerRepository;
import com.redhatschool.customermanagement.entity.Customer;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CustomerRepositoryTests {
	@Autowired
	private CustomerRepository repository;
	
	private Long customerId;
	
	@BeforeEach
	void addCustomer() {
		Customer customer = new Customer("Peter Quill", 100.0, LocalDateTime.now());
		Customer savedCustomer = repository.save(customer);
		customerId = savedCustomer.getCustomerId();
	}
	
	@Test
	void createCustomer() {
		int initialSize = repository.findAll().size();
		Customer customer = new Customer("Peter Quill", 190.0, LocalDateTime.now());
		repository.save(customer);
		assertEquals(initialSize + 1, repository.findAll().size());
	}
	
	@Test
	void updateCustomerById() {
		Optional<Customer> existingCustomer = repository.findById(customerId);
		assertTrue(existingCustomer.isPresent());
		Customer customer = existingCustomer.get();
		customer.setCustomerName("Groot");
		repository.save(customer);
		
		Optional<Customer> existingCustomerUpdated = repository.findById(customerId);
		assertTrue(existingCustomerUpdated.isPresent());
		assertEquals("Groot", existingCustomerUpdated.get().getCustomerName());
	}
	
	@Test
	void deleteCustomerById() {
		repository.deleteById(customerId);
		assertFalse(repository.findAll()
				.stream()
				.anyMatch(c -> c.getCustomerId().equals(customerId)));
	}
	
	@Test
	void getCustomerById() {
		Optional<Customer> customer = repository.findById(customerId);
		assertTrue(customer.isPresent());
		assertEquals("Peter Quill", customer.get().getCustomerName());
	}
	
	@Test
	void getAllCustomers() {
		assertTrue(repository.findAll().size() >= 1);
	}
	
	@Test
	void getCustomersBetweenBillingDate() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime date = LocalDateTime.parse("22/02/2022 00:30", pattern);
		Customer customer = new Customer("Vision", 109.0, date);
		repository.save(customer);
		
		LocalDateTime date1 = LocalDateTime.parse("21/02/2022 00:00", pattern);
		LocalDateTime date2 = LocalDateTime.parse("25/02/2022 00:00", pattern);
		List<Customer> customers = repository.findCustomersBetweenBillingDate(date1, date2);
		
		Predicate<Customer> betweenDatePredicate = c -> c.getBillingDate().isAfter(date1) && c.getBillingDate().isBefore(date2);
		
		assertTrue(customers.size() >= 1);
		assertTrue(customers.stream().allMatch(betweenDatePredicate));
	}

}
