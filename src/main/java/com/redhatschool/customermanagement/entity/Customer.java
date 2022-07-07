package com.redhatschool.customermanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMERS")
public class Customer {
	@Id
	@Column(name = "CUSTOMER_ID")
	@SequenceGenerator(name = "CustomerIdGenerator", initialValue = 1011, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerIdGenerator")
	private Long customerId;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "BILL_AMOUNT")
	private Double billAmount;
	
	@Column(name = "BILL_DATE")
	private LocalDateTime billingDate;

	public Customer() {
	}

	public Customer(String customerName, Double billAmount, LocalDateTime billingDate) {
		this.customerName = customerName;
		this.billAmount = billAmount;
		this.billingDate = billingDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public LocalDateTime getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(LocalDateTime billingDate) {
		this.billingDate = billingDate;
	}

	@Override
	public String toString() {
		return String.format("Customer [customerId=%s, customerName=%s, billAmount=%s, billingDate=%s]", customerId,
				customerName, billAmount, billingDate);
	}

}
