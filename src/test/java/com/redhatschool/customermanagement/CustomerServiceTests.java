package com.redhatschool.customermanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.redhatschool.customermanagement.dao.CustomerRepository;
import com.redhatschool.customermanagement.dto.CustomerDto;
import com.redhatschool.customermanagement.entity.Customer;
import com.redhatschool.customermanagement.exception.NotFoundException;
import com.redhatschool.customermanagement.service.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTests {
	@Mock
	private CustomerRepository repository;
	
	@InjectMocks
	private CustomerServiceImpl service;
	
	private Customer customer;
	private List<Customer> customers;
	
	private DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	@BeforeEach
	void initUsecase() {
		customer = new Customer("Peter Quill", 100.0, LocalDateTime.now());
		customer.setCustomerId(1000L);
		
		customers = new ArrayList<>();
		customers.add(customer);
	}
	
	@AfterEach
	void resetUsecase() {
		customer = null;
		customers = null;
	}
	
	@Test
	void createCustomerWithoutProvidingBillingDate() {
		when(repository.save(any(Customer.class))).thenReturn(customer);
		
		Customer newCustomer = service
				.createCustomer(new CustomerDto(customer.getCustomerName(), customer.getBillAmount()));
		
		assertThat(newCustomer.getCustomerName()).isNotNull();
		assertEquals("Peter Quill", customer.getCustomerName());
	}
	
	@Test
	void createCustomerWithBillingDate() {
		LocalDateTime date = LocalDateTime.parse("22/02/2022 00:00", pattern);
		customer.setBillingDate(date);
		when(repository.save(any(Customer.class))).thenReturn(customer);
		
		CustomerDto customerDto = new CustomerDto(customer.getCustomerName(), customer.getBillAmount());
		customerDto.setBillingDate("22/02/2022");
		Customer newCustomer = service.createCustomer(customerDto);
		
		assertThat(newCustomer.getCustomerName()).isNotNull();
		assertEquals("Peter Quill", customer.getCustomerName());
		assertEquals(date, newCustomer.getBillingDate());
	}
	
	@Test
	void updateCustomerWithoutProvidingBillingDate() {
		when(repository.save(any(Customer.class))).thenReturn(customer);
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(customer));
		
		customer.setBillAmount(200.0);
		customer.setCustomerName("Vision");
		
		Customer updatedCustomer = service.updateCustomerById(1000L, new CustomerDto("Vision", 200.0));
		
		assertThat(updatedCustomer).isNotNull();
		assertEquals("Vision", updatedCustomer.getCustomerName());
		assertEquals(200.0, updatedCustomer.getBillAmount());
	}
	
	@Test
	void updateCustomerProvidingBillingDate() {
		when(repository.save(any(Customer.class))).thenReturn(customer);
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(customer));
		
		customer.setBillAmount(200.0);
		customer.setCustomerName("Vision");
		LocalDateTime date = LocalDateTime.parse("22/02/2022 00:00", pattern);
		customer.setBillingDate(date);
		
		CustomerDto customerDto = new CustomerDto("Vision", 200.0);
		customerDto.setBillingDate("22/02/2022");
		Customer updatedCustomer = service.updateCustomerById(1000L, customerDto);
		
		assertThat(updatedCustomer).isNotNull();
		assertEquals("Vision", updatedCustomer.getCustomerName());
		assertEquals(200.0, updatedCustomer.getBillAmount());
		assertEquals(date, updatedCustomer.getBillingDate());
	}
	
	@Test
	void updateNonExistingCustomer() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		customer.setBillAmount(200.0);
		customer.setCustomerName("Vision");
		
		assertThrows(NotFoundException.class, () -> service
				.updateCustomerById(1000L, new CustomerDto("Vision", 200.0)));
		verify(repository, never()).save(any(Customer.class));
	}
	
	@Test
	void deleteCustomer() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(customer));
		
		Map<String, Object> res = service.deleteCustomerById(1000L);
		
		verify(repository, times(1)).deleteById(any(Long.class));
		assertEquals("Deleted successfully.", res.get("message"));
		assertEquals(customer, res.get("customer"));
	}
	
	@Test
	void deleteNonExistingCustomer() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> service.deleteCustomerById(1000L));
		verify(repository, never()).deleteById(any(Long.class));
	}
	
	@Test
	void getAllCustomers() {
		when(repository.findAll()).thenReturn(customers);
		
		List<Customer> allExistingCustomers = service.getAllCustomers();
		
		assertThat(allExistingCustomers).isNotNull();
		assertEquals(1, allExistingCustomers.size());
	}
	
	@Test
	void getCustomerById() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.of(customer));
		
		Customer foundCustomer = service.getCustomerById(1000L);
		
		assertThat(foundCustomer).isNotNull();
		assertEquals("Peter Quill", foundCustomer.getCustomerName());
	}
	
	@Test
	void getCustomerByNonExistingId() {
		when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> service.getCustomerById(1000L));
	}
	
	@Test
	void getCustomersBetweenBillingDate() {
		when(repository.findCustomersBetweenBillingDate(any(LocalDateTime.class), any(LocalDateTime.class)))
			.thenReturn(customers);
		
		List<Customer> foundCustomers = service
				.getAllCustomersWithinBillingDate(LocalDateTime.now(), LocalDateTime.MAX);
		
		assertThat(foundCustomers).isNotNull();
		assertEquals(1, foundCustomers.size());
	}
}
