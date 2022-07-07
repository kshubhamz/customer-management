package com.redhatschool.customermanagement.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.redhatschool.customermanagement.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("select c from Customer c where c.billingDate between ?1 and ?2")
	List<Customer> findCustomersBetweenBillingDate(LocalDateTime dateTime1, LocalDateTime dateTime2);
}
