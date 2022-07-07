package com.redhatschool.customermanagement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhatschool.customermanagement.controller.CustomerController;
import com.redhatschool.customermanagement.dto.CustomerDto;
import com.redhatschool.customermanagement.entity.Customer;
import com.redhatschool.customermanagement.exception.NotFoundException;
import com.redhatschool.customermanagement.service.CustomerService;

@WebMvcTest({ CustomerController.class })
@ExtendWith(MockitoExtension.class)
class CustomerControllerTests {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CustomerService service;
	
	private Customer customer;
	private List<Customer> customers;
	
	private String asJson(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
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
	void saveCustomerSuccess() throws Exception {
		CustomerDto customerDto = new CustomerDto("Peter Quill", 100.0);
		when(service.createCustomer(any(CustomerDto.class))).thenReturn(customer);
		
		mvc.perform(post("/customers")
				.content(asJson(customerDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.customerName", is("Peter Quill")));
	}
	
	@Test
	void saveCustomerFailure() throws Exception {
		CustomerDto customerDto = new CustomerDto("Peter Quill", 10.0);
		when(service.createCustomer(any(CustomerDto.class))).thenReturn(customer);
		
		mvc.perform(post("/customers")
				.content(asJson(customerDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()));
	}
	
	@Test
	void updateCustomerSuccess() throws Exception {
		when(service.updateCustomerById(any(Long.class), any(CustomerDto.class))).thenReturn(customer);
		
		customer.setCustomerName("Vision");
		CustomerDto customerDto = new CustomerDto(customer.getCustomerName(), customer.getBillAmount());
		
		mvc.perform(put("/customers/1000")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJson(customerDto))
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.customerName", is("Vision")));
	}
	
	
	@Test
	void updateCustomerFailure() throws Exception {
		when(service.updateCustomerById(any(Long.class), any(CustomerDto.class)))
			.thenThrow(new NotFoundException("Not Found"));
		
		customer.setCustomerName("Vision");
		CustomerDto customerDto = new CustomerDto(customer.getCustomerName(), customer.getBillAmount());
		
		mvc.perform(put("/customers/1000")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(asJson(customerDto))
				)
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()));
	}
	
	@Test
	void deleteCustomerByIdSuccess() throws Exception {
		when(service.deleteCustomerById(any(Long.class))).thenReturn(Map.of(
				"message", "Deleted successfully",
				"customer", customer
				));
		
		mvc.perform(delete("/customers/1000")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.message", is("Deleted successfully")))
		.andExpect(jsonPath("$.customer.customerName", is("Peter Quill")));
	}
	
	@Test
	void getAllCustomersSuccess() throws Exception {
		when(service.getAllCustomers()).thenReturn(customers);
		
		mvc.perform(get("/customers")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$[0].customerName", is("Peter Quill")));
	}
	
	@Test
	void getCustomerByIdSuccess() throws Exception {
		when(service.getCustomerById(any(Long.class))).thenReturn(customer);
		
		mvc.perform(get("/customers/1000")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.customerName", is("Peter Quill")));
	}
	
	@Test
	void getCustomerByIdFailure() throws Exception {
		when(service.getCustomerById(any(Long.class)))
			.thenThrow(new NotFoundException("Not Found"));
		
		mvc.perform(get("/customers/1000")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.message", notNullValue()));
	}
	
	@Test
	void getCustomersBetweenBillingDateSuccess() throws Exception {
		when(service.getAllCustomersWithinBillingDate(any(LocalDateTime.class), any(LocalDateTime.class)))
			.thenReturn(customers);
		
		mvc.perform(get("/customers/within-billing-date?start-date=11/01/2011&end-date=20/02/2020")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$[0].customerName", is("Peter Quill")));
	}
	
	@Test
	void getCustomersBetweenBillingDateFailure() throws Exception {
		when(service.getAllCustomersWithinBillingDate(any(LocalDateTime.class), any(LocalDateTime.class)))
			.thenReturn(customers);
		
		mvc.perform(get("/customers/within-billing-date?start-date=11/01/2011")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isBadRequest());
	}

}
