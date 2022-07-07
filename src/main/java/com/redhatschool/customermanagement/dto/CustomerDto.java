package com.redhatschool.customermanagement.dto;

import javax.validation.constraints.NotEmpty;

import com.redhatschool.customermanagement.validator.CustomerBill;

public class CustomerDto {
	@NotEmpty
	private String name;

	@CustomerBill
	private Double bill;

	private String billingDate;

	public CustomerDto() {
	}

	public CustomerDto(@NotEmpty String name, Double bill) {
		this.name = name;
		this.bill = bill;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBill() {
		return bill;
	}

	public void setBill(Double bill) {
		this.bill = bill;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	@Override
	public String toString() {
		return String.format("CustomerDto [name=%s, bill=%s, billingDate=%s]", name, bill, billingDate);
	}

}
